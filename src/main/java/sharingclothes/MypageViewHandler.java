package sharingclothes;

import sharingclothes.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setClothesId(ordered.getId());
                mypage.setClothesId(ordered.getClothesId());
                mypage.setQty(ordered.getQty());
                mypage.setStatus("Shipped");
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenShipped_then_UPDATE_1(@Payload Shipped shipped) {
        try {
            if (shipped.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByClothesId(shipped.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    System.out.println("마이페이지 테스트 getStatus : "+ shipped.getStatus());
                    mypage.setStatus(shipped.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCancelled_then_UPDATE_2(@Payload DeliveryCancelled deliveryCancelled) {
        try {
            if (deliveryCancelled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByClothesId(deliveryCancelled.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setDeliveryId(deliveryCancelled.getId());
                    mypage.setStatus(deliveryCancelled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenShipped_then_UPDATE_1(@Payload Shipped shipped) {
//        try {
//            if (shipped.isMe()) {
//                // view 객체 조회
//                List<Mypage> mypageList = mypageRepository.findByClothesId(shipped.getClothesId());
//                for(Mypage mypage : mypageList){
//                    // view 객체에 이벤트의 eventDirectValue 를 set 함
//                    mypage.setDeliveryId(shipped.getId());
//                    mypage.setStatus(shipped.getStatus());
//                    // view 레파지 토리에 save
//                    mypageRepository.save(mypage);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenDeliveryCancelled_then_UPDATE_2(@Payload DeliveryCancelled deliveryCancelled) {
//        try {
//            if (deliveryCancelled.isMe()) {
//                // view 객체 조회
//                List<Mypage> mypageList = mypageRepository.findByClothesId(deliveryCancelled.getClothesId());
//                for(Mypage mypage : mypageList){
//                    // view 객체에 이벤트의 eventDirectValue 를 set 함
//                    mypage.setStatus(deliveryCancelled.getStatus());
//                    // view 레파지 토리에 save
//                    mypageRepository.save(mypage);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCancelled_then_DELETE_1(@Payload DeliveryCancelled deliveryCancelled) {
        try {
            if (deliveryCancelled.isMe()) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}