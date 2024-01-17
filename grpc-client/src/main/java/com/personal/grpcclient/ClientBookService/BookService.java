package com.personal.grpcclient.ClientBookService;



import com.project.BookRequest;
import com.project.BookResponse;
import com.project.BookServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @GrpcClient("myClient")
    private  BookServiceGrpc.BookServiceBlockingStub serviceBlockingStub;
    public String  getBookNameFromGrpc(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();

        BookServiceGrpc.BookServiceBlockingStub stub = BookServiceGrpc.newBlockingStub(channel);

        BookResponse bookResponse = stub.getBook(BookRequest.newBuilder().setBookId("1").build());
        channel.shutdown();
        return bookResponse.getName();
    }
    public String  getBookNameFromGrpc2(){

        return serviceBlockingStub.getBook(BookRequest.newBuilder().setBookId("1").build()).getName();
//        return bookResponse.getName();
    }
}
