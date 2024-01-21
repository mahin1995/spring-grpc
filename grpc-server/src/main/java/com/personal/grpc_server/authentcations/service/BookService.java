package com.personal.grpc_server.authentcations.service;





import com.project.BookRequest;
import com.project.BookResponse;
import com.project.BookServiceGrpc;
import com.project.constants.Type;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class BookService extends BookServiceGrpc.BookServiceImplBase{
    @Override
    public void getBook(BookRequest request , StreamObserver<BookResponse> responseObserver) {
//        super.getBook(request , responseObserver);

        // We have mocked the employee data.
        // In real world this should be fetched from database or from some other source
        BookResponse empResp = BookResponse.newBuilder().setBookId(request.getBookId()).setName("javainuse")
                .setType(Type.AUTOBIOGRAPHY).build();

        // set the response object
        responseObserver.onNext(empResp);

        // mark process is completed
        responseObserver.onCompleted();
    }
}
