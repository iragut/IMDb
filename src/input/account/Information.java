package org.example.input.account;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Information {
    private String name;
    private String country;
    private Integer age;
    private String gender;
    private Date birthDate;
    private CredentialsInput credentials;
    private Information(InformationBuilder builder){
        this.name = builder.name;
        this.country = builder.country;
        this.age = builder.age;
        this.gender = builder.gender;
        this.birthDate = builder.birthDate;
        this.credentials = builder.credentials;
    }

    @Getter
    @Setter
    public static class InformationBuilder{
        private String name;
        private String country;
        private Integer age;
        private String gender;
        private Date birthDate;
        private CredentialsInput credentials;

        public InformationBuilder(CredentialsInput credentials, String name){
            this.name = name;
            this.credentials = credentials;
            this.country = "";
            this.gender = "";
            this.age = 0;

        }
        public InformationBuilder country(String country){
            this.country = country;
            return this;
        }
        public InformationBuilder age(Integer age){
            this.age = age;
            return this;
        }
        public InformationBuilder gender(String gender){
            this.gender = gender;
            return this;
        }
        public InformationBuilder birthDate(Date birthDate){
            this.birthDate = birthDate;
            return this;
        }
        public Information build(){
            return new Information(this);
        }
    }

}
