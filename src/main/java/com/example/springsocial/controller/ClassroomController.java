package com.example.springsocial.controller;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.Classroom;
import com.example.springsocial.model.ClassroomRespone;
import com.example.springsocial.model.Invitation;
import com.example.springsocial.payload.*;
import com.example.springsocial.repository.InvitationRepository;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.CustomCourseDetailsService;
import com.example.springsocial.security.UserPrincipal;
import com.example.springsocial.util.ConvertStringToArrayList;
import com.example.springsocial.util.RandomStringSingleton;
import com.example.springsocial.util.StringToTextArrayPosgre;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.ClassroomRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CustomUserDetailsService;
import com.example.springsocial.util.StringToTextArrayPosgre;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.springsocial.repository.UserRepository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/courses")
public class ClassroomController {
    //design pattern spring singleton
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    //design pattern spring singleton
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private StringToTextArrayPosgre stringToTextArrayPosgre;
    @Autowired
    private CustomCourseDetailsService customCourseDetailsService;
    @Autowired
    private ConvertStringToArrayList convertStringToArrayList;
    @Autowired
    private ClassroomRespone course = new ClassroomRespone();

    @Autowired
    private ClassroomRespone[] courses = new ClassroomRespone[0];

    @Autowired
    RandomStringSingleton randomStringSingleton = RandomStringSingleton.getInstance();
    private static final String[] EmptyArrayString = {};


    private static void printVariableType(Object variable) {
        // Get the runtime type of the variable
        Class<?> variableType = variable.getClass();

        // Print the variable type
        System.out.println("Variable type: " + variableType.getName());
    }

    public ClassroomRespone getCourse() {
        return course;
    }

    public void setCourse(ClassroomRespone course) {
        this.course = course;
    }

    public ClassroomRespone[] getCourses() {
        return courses;
    }

    public void setCourses(ClassroomRespone[] courses) {
        this.courses = courses;
    }

    @GetMapping()
    public ResponseEntity<?> getAllClassroomByOwner(@CurrentUser UserPrincipal userPrincipal) {
        Classroom[] classroom1 = new Classroom[100];
        Classroom[] classroom2 = new Classroom[100];
        List<Classroom> allClassroom = classroomRepository.findAll();
        int student_length = 0, teacher_length = 0;
        for (int i = 0; i < allClassroom.size(); i++) {

            if (allClassroom.get(i).getStudents() == null) {
                continue;
            } else {
                String[] splitStr = allClassroom.get(i).getStudents().split(",");
                for (int j = 0; j < splitStr.length; j++) {
                    if (splitStr[j].equals(userPrincipal.get_id())) {
                        classroom2[student_length] = allClassroom.get(i);
                        student_length++;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < allClassroom.size(); i++) {
            if (allClassroom.get(i).getTeachers() == null) {
                continue;
            } else {
                String[] splitStr = allClassroom.get(i).getTeachers().split(",");
                for (int j = 0; j < splitStr.length; j++) {
                    if (splitStr[j].equals(userPrincipal.get_id())) {
                        classroom1[teacher_length] = allClassroom.get(i);
                        teacher_length++;
                        break;
                    }
                }
            }
        }


        Classroom[] classroom = new Classroom[teacher_length + student_length];
        System.arraycopy(classroom1, 0, classroom, 0, teacher_length);
        System.arraycopy(classroom2, 0, classroom, teacher_length, student_length);


        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "No classroom found"));
        } else {
            this.courses = new ClassroomRespone[classroom.length];
            for (int i = 0; i < classroom.length; i++) {
                this.courses[i] = new ClassroomRespone();
            }
            for (int i = 0; i < classroom.length; i++) {
                String[] strStudents = classroom[i].getStudents() != null
                        ? convertStringToArrayList.convertToArrayList(classroom[i].getStudents()).toArray(new String[0])
                        : new String[0];
                String[] strTeachers = classroom[i].getTeachers() != null
                        ? convertStringToArrayList.convertToArrayList(classroom[i].getTeachers()).toArray(new String[0])
                        : new String[0];
                String[] strAssignments = classroom[i].getAssignments() != null
                        ? convertStringToArrayList.convertToArrayList(classroom[i].getAssignments()).toArray(new String[0])
                        : new String[0];
                String[] strStudentsIds = classroom[i].getStudentsIds() != null
                        ? convertStringToArrayList.convertToArrayList(classroom[i].getStudentsIds()).toArray(new String[0])
                        : new String[0];
                this.courses[i].setOwner(customUserDetailsService.loadUserBy_id(classroom[i].getOwner()));

                User[] userTeachers = new User[strTeachers.length];
                User[] userStudentsIds = new User[strStudentsIds.length];
                for (int j = 0; j < strTeachers.length; j++) {
                    userTeachers[j] = customUserDetailsService.loadUserBy_id(strTeachers[j]);


                }
                for (int j = 0; j < strStudentsIds.length; j++) {
                    userStudentsIds[j] = customUserDetailsService.loadUserBy_id(strStudentsIds[j]);
                }

                this.courses[i].setTeachers(strTeachers);
                this.courses[i].setStudents(strStudents);
                this.courses[i].setStudentIds(userStudentsIds);
                this.courses[i].set_id(classroom[i].get_id());
                this.courses[i].setName(classroom[i].getName());
                this.courses[i].setDescription(classroom[i].getDescription());
                this.courses[i].setSlug(classroom[i].getSlug());
                this.courses[i].setJoinId(classroom[i].getJoinId());
                this.courses[i].setCreatedAt(classroom[i].getCreatedAt());
                this.courses[i].setUpdateAt(classroom[i].getUpdateAt());
                this.courses[i].setAssignments(strAssignments);
            }



            return ResponseEntity.ok(new ApiClassroomResponse(true, 200, courses));
        }
    }


    @PostMapping("/store")
    public ResponseEntity<?> createClassroom(@Valid @RequestBody ClassroomRequest classroomRequest, @CurrentUser UserPrincipal userPrincipal) {
        //design pattern  singleton
        String randomInvitationId = randomStringSingleton.generateRandomString(24);
        ;
        String randomCodeInvitationCode = randomStringSingleton.generateRandomString(8);
        ;
        String randomCodeJoinId = randomStringSingleton.generateRandomString(8);
        ;
        String randomCodeIdClass = randomStringSingleton.generateRandomString(24);
        ;
        LocalDate currentTime = LocalDate.now();

        Classroom classroom = new Classroom();
        Invitation invitation = new Invitation();


        classroom.set_id(randomCodeIdClass);

        classroom.setName(classroomRequest.getName());

        classroom.setDescription(classroomRequest.getDescription());

        classroom.setJoinId(randomCodeJoinId);

        classroom.setCreatedAt(currentTime);

        classroom.setUpdateAt(currentTime);

        classroom.setSlug(classroomRequest.getName().toLowerCase());

        classroom.setOwner(userPrincipal.get_id());


        classroom.setTeachers(userPrincipal.get_id());


        Classroom result = classroomRepository.save(classroom);

        invitation.set_id(randomInvitationId);
        invitation.setCourseId(result.get_id());
        invitation.setType(1);
        invitation.setInviteCode(randomCodeInvitationCode);
        invitation.setCreated_at(currentTime);
        invitation.setUpdate_at(currentTime);
        invitationRepository.save(invitation);


        String[] strStudents = classroom.getStudents() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getStudents()).toArray(new String[0])
                : null;
        String[] strTeachers = classroom.getTeachers() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getTeachers()).toArray(new String[0])
                : null;
        String[] strAssignments = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : null;
        String[] strStudentsIds = classroom.getStudentsIds() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getStudentsIds()).toArray(new String[0])
                : null;

        this.course.setAssignments(strAssignments);
        this.course.setStudents(strStudents);
        this.course.setStudentIds(strStudentsIds);
        this.course.setTeachers(strTeachers);
        this.course.setOwner(classroom.getOwner());
        this.course.set_id(classroom.get_id());
        this.course.setName(classroom.getName());
        this.course.setDescription(classroom.getDescription());
        this.course.setSlug(classroom.getSlug());
        this.course.setJoinId(classroom.getJoinId());
        this.course.setCreatedAt(classroom.getCreatedAt());
        this.course.setUpdateAt(classroom.getUpdateAt());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(result.get_id()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiClassroomResponse(true, 200, course));


    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> showOneClassroom(@PathVariable String slug) {
        Classroom classroom = classroomRepository.findBySlug(slug);
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "No class found"));
        } else {
            String[] strStudents = classroom.getStudents() != null
                    ? convertStringToArrayList.convertToArrayList(classroom.getStudents()).toArray(new String[0])
                    : new String[0];
            String[] strTeachers = classroom.getTeachers() != null
                    ? convertStringToArrayList.convertToArrayList(classroom.getTeachers()).toArray(new String[0])
                    : new String[0];
            String[] strAssignments = classroom.getAssignments() != null
                    ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                    : new String[0];
            String[] strStudentsIds = classroom.getStudentsIds() != null
                    ? convertStringToArrayList.convertToArrayList(classroom.getStudentsIds()).toArray(new String[0])
                    : new String[0];
            User[] userTeachers = new User[strTeachers.length];
            User[] userStudents = new User[strStudents.length];
            User[] userStudentsIds = new User[strStudentsIds.length];

            for (int i = 0; i < strTeachers.length; i++) {
                userTeachers[i] = customUserDetailsService.loadUserBy_id(strTeachers[i]);
            }

            for (int i = 0; i < strStudents.length; i++) {
                userStudents[i] = customUserDetailsService.loadUserBy_id(strStudents[i]);
            }

            for (int i = 0; i < strStudentsIds.length; i++) {
                userStudentsIds[i] = customUserDetailsService.loadUserBy_id(strStudentsIds[i]);
            }

            this.course.setTeachers(userTeachers);
            this.course.setStudents(userStudents);

            this.course.setStudentIds(userStudentsIds);
            this.course.setOwner(classroom.getOwner());
            this.course.set_id(classroom.get_id());
            this.course.setName(classroom.getName());
            this.course.setDescription(classroom.getDescription());
            this.course.setSlug(classroom.getSlug());
            this.course.setJoinId(classroom.getJoinId());
            this.course.setCreatedAt(classroom.getCreatedAt());
            this.course.setUpdateAt(classroom.getUpdateAt());
            this.course.setAssignments(strAssignments);


            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .buildAndExpand(classroom.get_id()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiClassroomResponse(true, 200, course));
        }


    }


    @GetMapping({"/{classId}/invitation"})
    public ResponseEntity<?> ShowClassroomCode(@PathVariable String classId) {
        Invitation invitation = invitationRepository.findByCourseId(classId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(invitation.get_id()).toUri();
        if (invitation == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "No class found"));
        } else {
            return ResponseEntity.created(location).body(new InvitationRespone(true, 200, invitation));
        }
    }

    @GetMapping({"/join/{id}"})
    public ResponseEntity<?> joinCourse(@PathVariable String id, @CurrentUser UserPrincipal userPrincipal) {
        User user = customUserDetailsService.loadUserBy_id(userPrincipal.get_id());
        Invitation invitation = invitationRepository.findByInviteCode(id);

        if (invitation == null) {
            System.out.println("invite not founddddddddddd");
            return ResponseEntity.ok(new ApiResponse(200, false, "Invite not found"));
        }
        Classroom classroom = classroomRepository.findBy_id(invitation.getCourseId()).orElseThrow(
                () -> new ResourceNotFoundException("courses", "_id", invitation.getCourseId()));
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (invitation.getType() == 1) {
            if (classroom.getTeachers() == user.get_id())
                return ResponseEntity.ok(new ApiResponse(200, false, "Already a teacher"));
            if (classroom.getStudents() != null && Arrays.asList(classroom.getStudents()).contains(user.get_id()))
                return ResponseEntity.ok(new ApiResponse(200, false, "You have already joined this course"));
            if (classroom.getStudents() == null) {
                classroom.setStudents(user.get_id());
            } else {
                classroom.setStudents(classroom.getStudents() + "," + user.get_id());
            }

        } else if (invitation.getType() == 0) {
            if (classroom.getTeachers() != null && Arrays.asList(classroom.getTeachers()).contains(user.get_id()))
                return ResponseEntity.ok(new ApiResponse(200, false, "Already a teacher"));
            if (classroom.getTeachers() == null) {
                classroom.setTeachers(user.get_id());
            } else {
                classroom.setTeachers(classroom.getTeachers() + "," + user.get_id());
            }
        }
        classroomRepository.save(classroom);
        String[] strStudents = classroom.getStudents() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getStudents()).toArray(new String[0])
                : new String[0];
        String[] strTeachers = classroom.getTeachers() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getTeachers()).toArray(new String[0])
                : new String[0];
        String[] strAssignments = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];
        String[] strStudentsIds = classroom.getStudentsIds() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getStudentsIds()).toArray(new String[0])
                : new String[0];
        User[] userTeachers = new User[strTeachers.length];
        User[] userStudents = new User[strStudents.length];
        User[] userStudentsIds = new User[strStudentsIds.length];

        for (int i = 0; i < strTeachers.length; i++) {
            userTeachers[i] = customUserDetailsService.loadUserBy_id(strTeachers[i]);
        }

        for (int i = 0; i < strStudents.length; i++) {
            userStudents[i] = customUserDetailsService.loadUserBy_id(strStudents[i]);
        }

        for (int i = 0; i < strStudentsIds.length; i++) {
            userStudentsIds[i] = customUserDetailsService.loadUserBy_id(strStudentsIds[i]);
        }

        this.course.setTeachers(userTeachers);
        this.course.setStudents(userStudents);

        this.course.setStudentIds(userStudentsIds);
        this.course.setOwner(classroom.getOwner());
        this.course.set_id(classroom.get_id());
        this.course.setName(classroom.getName());
        this.course.setDescription(classroom.getDescription());
        this.course.setSlug(classroom.getSlug());
        this.course.setJoinId(classroom.getJoinId());
        this.course.setCreatedAt(classroom.getCreatedAt());
        this.course.setUpdateAt(classroom.getUpdateAt());
        this.course.setAssignments(strAssignments);
        return ResponseEntity.ok(new ApiClassroomResponse(true, 200, this.course));


    }
}
