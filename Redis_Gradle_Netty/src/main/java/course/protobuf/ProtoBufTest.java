package course.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        InfoStudent.Student stu1 = InfoStudent.Student.newBuilder()
                .setName("Ada").setAge(20)
                .setAddress("Somewhere").build();
        byte[] stu1BA = stu1.toByteArray();
        InfoStudent.Student stu2 = InfoStudent.Student.parseFrom(stu1BA);
        System.out.println(stu2);
    }
}
