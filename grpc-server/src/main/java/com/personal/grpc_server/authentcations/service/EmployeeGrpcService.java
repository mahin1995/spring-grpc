package com.personal.grpc_server.authentcations.service;

import com.google.protobuf.Empty;
import com.project.Employee;
import com.project.EmployeeServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class EmployeeGrpcService extends EmployeeServiceGrpc.EmployeeServiceImplBase {
    @Override
    public void getEmployeeInfo(Empty request , StreamObserver<Employee> responseObserver) {
        responseObserver.onNext(Employee.newBuilder().setName("DevProblems").setSalary(123).build());
        responseObserver.onCompleted();
    }

}
