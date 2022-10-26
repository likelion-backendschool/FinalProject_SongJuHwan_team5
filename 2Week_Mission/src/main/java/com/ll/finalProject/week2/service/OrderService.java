package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.*;
import com.ll.finalProject.week2.repository.OrderItemRepository;
import com.ll.finalProject.week2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemService cartItemService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberService memberService;
    private final MyBookService myBookService;


    public void createFromCart(Member member) {
        List<CartItem> cartItemList = cartItemService.getItemsByMember(member);
        Ordered ordered = new Ordered();
        ordered.setMember(member);
        ordered.setIsCanceled(false);
        ordered.setIsPaid(false);
        ordered.setIsRefunded(false);
        int totalPrice = 0;
        for(CartItem cartItem : cartItemList){
            totalPrice += cartItem.getProduct().getPrice();
        }
        ordered.setCalculatePayPrice(totalPrice);
        ordered.setReadyStatus("준비");
        ordered.setName(cartItemList.get(0).getProduct().getSubject() + " 외 " + (cartItemList.size()-1) + "권");
        orderRepository.save(ordered);
        for (CartItem cartItem : cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrdered(ordered);
            orderItemRepository.save(orderItem);
            cartItemService.delete(cartItem);
        }
    }

    public List<Ordered> findAllByMember(Member member) {
        return orderRepository.findAllByMember(member);
    }

    public Ordered findById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(null);
    }

    public void payByTossPayments(Ordered order, int payPriceRestCash) {

        Member member = order.getMember();

        int payPrice = order.getCalculatePayPrice();
        int pgPayPrice = payPrice - payPriceRestCash;

        memberService.addCash(member, pgPayPrice, "주문__%d__충전__토스".formatted(order.getId()));
        memberService.addCash(member, pgPayPrice * -1 ,"주문__%d__사용__토스".formatted(order.getId()));


        if( payPriceRestCash > 0){
            memberService.addCash(member, payPriceRestCash * -1 , "주문__%d__사용__예치금".formatted(order.getId()));
        }

        order.setIsPaid(true);
        order.setReadyStatus("결제완료");

        List<OrderItem> orderItemList = orderItemRepository.findAllByOrdered(order);

        for(OrderItem orderItem : orderItemList){
            MyBook myBook = new MyBook();
            myBook.setProduct(orderItem.getProduct());
            myBook.setMember(orderItem.getOrdered().getMember());
            myBookService.addBook(myBook);
        }

        orderRepository.save(order);
    }

    @Async
    public void waitToRefund(Ordered order) throws InterruptedException {
        Thread.sleep(1000 * 60 * 60);
        order.setReadyStatus("결제확정");
        orderRepository.save(order);
    }

    public void payByRestCashOnly(Long orderId) {
        Ordered order = findById(orderId);
        order.setIsPaid(true);
        order.setReadyStatus("결제완료");
        orderRepository.save(order);
        memberService.addCash(order.getMember(), -order.getCalculatePayPrice(), "결제_전액_예치금");
    }

    public void cancel(Long orderId) {
        Ordered order = findById(orderId);
        order.setIsCanceled(true);
        order.setReadyStatus("주문취소");
        orderRepository.save(order);
    }

    public void refund(Long orderId) {
        Ordered order = findById(orderId);
        order.setIsRefunded(true);
        order.setReadyStatus("주문환불");
        orderRepository.save(order);
        memberService.addCash(order.getMember(), order.getCalculatePayPrice(), "환불__전액__예치금");
    }
}
