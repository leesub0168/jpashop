package jpabook.jpashop.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    // 값 타입은 immutable(불변) 하게 설계 하는게 좋음.
    // 생성시에 값을 모두 세팅하고 getter로 값을 제공하고 setter는 제공하지 않아야 함.


    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
