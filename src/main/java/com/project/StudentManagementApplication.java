package com.project;

import com.project.entity.concretes.user.UserRole;
import com.project.entity.enums.Gender;
import com.project.entity.enums.RoleType;
import com.project.payload.request.user.UserRequest;
import com.project.repository.UserRoleRepository;
import com.project.service.UserRoleService;
import com.project.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class StudentManagementApplication implements CommandLineRunner {
    private final UserRoleService userRoleService;
    private final UserRoleRepository userRoleRepository;
    private final UserService userService;

    public StudentManagementApplication(UserRoleService userRoleService, UserRoleRepository userRoleRepository, UserService userService) {
        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRoleService.getAllUserRole().isEmpty()){
            //admin
            UserRole admin = new UserRole();
            admin.setRoleType(RoleType.ADMIN);
            admin.setRoleName("Admin");
            userRoleRepository.save(admin);
            //dean
            UserRole dean = new UserRole();
            dean.setRoleType(RoleType.MANAGER);
            dean.setRoleName("Dean");
            userRoleRepository.save(dean);
            //vice-dean
            UserRole viceDean = new UserRole();
            viceDean.setRoleType(RoleType.ASSISTANT_MANAGER);
            viceDean.setRoleName("ViceDean");
            userRoleRepository.save(viceDean);
            //student
            UserRole student = new UserRole();
            student.setRoleType(RoleType.STUDENT);
            student.setRoleName("Student");
            userRoleRepository.save(student);
            //teacher
            UserRole teacher = new UserRole();
            teacher.setRoleType(RoleType.TEACHER);
            teacher.setRoleName("Teacher");
            userRoleRepository.save(teacher);

        }
        if (userService.countAllAdmins()==0){
         UserRequest adminRequest = new UserRequest();
        adminRequest.setUsername("SuperAdmin");
        adminRequest.setEmail("admin@gmail.com");
        adminRequest.setSsn("111-11-1111");
        adminRequest.setPassword("12345678");
        adminRequest.setName("Zafer");
        adminRequest.setSurname("Kanbur");
        adminRequest.setPhoneNumber("111-111-1111");
        adminRequest.setGender(Gender.MALE);
        adminRequest.setBirthDay(LocalDate.of(1980,2,2));
        adminRequest.setBirthPlace("ISTANBUL");

    userService.saveUser(adminRequest,"Admin");
}
    }
}
