package com.thepen.thepen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Registration {
    @JsonProperty("userName")
    String userName;
    @JsonProperty("password")
    String password;
    @JsonProperty("address")
    String address;
    @JsonProperty("phone")
    String phone;
    @JsonProperty("city")
    String city;
    @JsonProperty("state")
    String state;
    @JsonProperty("country")
    String country;
    @JsonProperty("roleId")
    String roleId;
    @JsonProperty("zipCode")
    String zipCode;
    @JsonProperty("firstName")
    String firstName;
    @JsonProperty("lastName")
    String lastName;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("parentName")
    private String parentName;
    @JsonProperty("student_std")
    private String student_std;
    @JsonProperty("objective")
    private String objective;
    @JsonProperty("schoolName")
    private String schoolName;
    @JsonProperty("declarationBystudent")
    private boolean declarationBystudent;
    @JsonProperty("declarationByparent")
    private boolean declarationByparent;
    @JsonProperty("commitement")
    private String commitement;
    @JsonProperty("referenceName")
    private String referenceName;
    @JsonProperty("parentNote")
    private String parentNote;
    @JsonProperty("termsAndcondtion")
    private boolean termsAndcondtion;

    @JsonProperty("exptSubject")
    private String exptSubject;
    @JsonProperty("university")
    private String university;
    @JsonProperty("degree")
    private String degree;
    @JsonProperty("experience")
    private String experience;
    @JsonProperty("declarationByTeacher")
    private boolean declarationByTeacher;
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getObjective() {
        return objective;
    }
    public void setObjective(String objective) {
        this.objective = objective;
    }
    public String getSchoolName() {
        return schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public boolean isDeclarationBystudent() {
        return declarationBystudent;
    }
    public void setDeclarationBystudent(boolean declarationBystudent) {
        this.declarationBystudent = declarationBystudent;
    }
    public boolean isDeclarationByparent() {
        return declarationByparent;
    }
    public void setDeclarationByparent(boolean declarationByparent) {
        this.declarationByparent = declarationByparent;
    }
    public String getCommitement() {
        return commitement;
    }
    public void setCommitement(String commitement) {
        this.commitement = commitement;
    }
    public String getReferenceName() {
        return referenceName;
    }
    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }
    public String getParentNote() {
        return parentNote;
    }
    public void setParentNote(String parentNote) {
        this.parentNote = parentNote;
    }

    public boolean isTermsAndcondtion() {
        return termsAndcondtion;
    }
    public void setTermsAndcondtion(boolean termsAndcondtion) {
        this.termsAndcondtion = termsAndcondtion;
    }
    public String getStudent_std() {
        return student_std;
    }
    public void setStudent_std(String student_std) {
        this.student_std = student_std;
    }

    @JsonProperty("registered")
    boolean registered;
    public Object isRegistered_flag() {
        return registered;
    }

    public void setExptSubject(String exptSubject) {
        this.exptSubject = exptSubject;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public void setDeclarationByTeacher(boolean declarationByTeacher) {
        this.declarationByTeacher = declarationByTeacher;
    }
}