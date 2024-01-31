package com.example.springsocial.controller;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.*;
import com.example.springsocial.payload.*;
import com.example.springsocial.repository.*;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.CustomCourseDetailsService;
import com.example.springsocial.security.UserPrincipal;
import com.example.springsocial.util.ConvertStringToArrayList;
import com.example.springsocial.util.RandomStringSingleton;
import com.example.springsocial.util.StringToTextArrayPosgre;
import com.example.springsocial.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssignmentV2Repository assignmentV2Repository;
    @Autowired
    private StringToTextArrayPosgre stringToTextArrayPosgre;
    @Autowired
    private CustomCourseDetailsService customCourseDetailsService;
    @Autowired
    private ConvertStringToArrayList convertStringToArrayList;
    @Autowired
    private ClassroomV2 course = new ClassroomV2();

    @Autowired
    private ClassroomV2[] courses = new ClassroomV2[0];
    @Autowired
    private AssignmentV2 assignmentRespone = new AssignmentV2();
    @Autowired
    private AssignmentV2[] assignmentRespones = new AssignmentV2[0];


    @Autowired
    RandomStringSingleton randomStringSingleton = RandomStringSingleton.getInstance();
    private static final String[] EmptyArrayString = {};


    private static void printVariableType(Object variable) {
        // Get the runtime type of the variable
        Class<?> variableType = variable.getClass();

        // Print the variable type
        System.out.println("Variable type: " + variableType.getName());
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
            this.courses = new ClassroomV2[classroom.length];
            for (int i = 0; i < classroom.length; i++) {
                this.courses[i] = new ClassroomV2();
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

                for (int j = 0; j < strTeachers.length; j++) {
                    userTeachers[j] = customUserDetailsService.loadUserBy_id(strTeachers[j]);


                }

                if(strTeachers.length!=0){
                    this.courses[i].setTeachers(strTeachers);
                }
                if(strStudents.length!=0){
                    this.courses[i].setStudents(strStudents);
                }
                if(strStudentsIds.length!=0){
                    this.courses[i].setStudentIds(strStudentsIds);
                }
                if(strAssignments.length!=0){
                    this.courses[i].setAssignments(strAssignments);
                }


                this.courses[i].set_id(classroom[i].get_id());
                //System.out.println("dddddddd");
                this.courses[i].setName(classroom[i].getName());
                //System.out.println("eeeeeeeeeeeee");
                this.courses[i].setDescription(classroom[i].getDescription());
                //System.out.println("fffffffffffff");
                this.courses[i].setSlug(classroom[i].getSlug());
                //System.out.println("gggggggggggggg");
                this.courses[i].setJoinId(classroom[i].getJoinId());
                //System.out.println("hhhhhhhhhhhhhhhhh");
                this.courses[i].setCreatedAt(classroom[i].getCreatedAt());
                //System.out.println("jjjjjjjjjjjjjjjjj");
                this.courses[i].setUpdateAt(classroom[i].getUpdateAt());
                //System.out.println("kkkkkkkkkkkkkkkkk");
                //System.out.println("lllllllllllllllll");

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
            for(int i=0;i<strStudentsIds.length;i++){
                System.out.println(strStudentsIds[i]);
            }
            User[] userTeachers = new User[strTeachers.length];
            User[] userStudents = new User[strStudents.length];
            User[] userStudentsIds = new User[strStudentsIds.length];

            for (int i = 0; i < strTeachers.length; i++) {
                userTeachers[i] = customUserDetailsService.loadUserBy_id(strTeachers[i]);
            }

            for (int i = 0; i < strStudents.length; i++) {
                userStudents[i] = customUserDetailsService.loadUserBy_id(strStudents[i]);
            }



            this.course.setTeachers(userTeachers);
            this.course.setStudents(userStudents);
            if(strStudentsIds.length!=0){
                this.course.setStudentIds(strStudentsIds);
            }
            else if(strStudentsIds.length==0){
                this.course.setStudentIds(new User[0]);
            }
            this.course.setOwner(classroom.getOwner());
            this.course.set_id(classroom.get_id());
            this.course.setName(classroom.getName());
            this.course.setDescription(classroom.getDescription());
            this.course.setSlug(classroom.getSlug());
            this.course.setJoinId(classroom.getJoinId());
            this.course.setCreatedAt(classroom.getCreatedAt());
            this.course.setUpdateAt(classroom.getUpdateAt());
            Assignment[] assignments = new Assignment[strAssignments.length];
            AssignmentV2[] assignmentsV2= new AssignmentV2[strAssignments.length];
            for (int i = 0; i < strAssignments.length; i++) {
                assignmentsV2[i]=new AssignmentV2();
                assignments[i]=assignmentRepository.findBy_id(strAssignments[i]);
                if (assignments[i] != null) {
                    if(assignments[i].getGrades()==null){
                        assignmentsV2[i].setGrades(new User[0]);
                    }
                    assignmentsV2[i].set_id(assignments[i].get_id());
                    assignmentsV2[i].setName(assignments[i].getName());
                    assignmentsV2[i].setPoint(assignments[i].getPoint());
                    assignmentsV2[i].setCreated_at(assignments[i].getCreatedAt());
                    assignmentsV2[i].setUpdate_at(assignments[i].getUpdateAt());

                }

            }
            if(assignmentsV2.length!=0)
                this.course.setAssignments(assignmentsV2);
            else if(assignmentsV2.length==0){
                this.course.setAssignments(new AssignmentV2[0]);
            }


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
    @PostMapping({"/{slug}/assignment"})
    public ResponseEntity<?> createAssignment(@PathVariable String slug, @Valid @RequestBody AssignmentRequest assignmentRequest, @CurrentUser UserPrincipal userPrincipal) {
        Classroom classroom = classroomRepository.findBySlug(slug);
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {
            System.out.println("TEACHER"+classroom.getTeachers());
            System.out.println("USER"+userPrincipal.get_id());
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorized"));
        }

        Assignment assignment = new Assignment();
        String randomAssignmentId = randomStringSingleton.generateRandomString(24);
        assignment.set_id(randomAssignmentId);
        assignment.setName(assignmentRequest.getName());
        assignment.setPoint(assignmentRequest.getPoint());
        assignment.setCreatedAt(LocalDate.now());
        assignment.setUpdateAt(LocalDate.now());
        assignmentRepository.save(assignment);
        if (classroom.getAssignments() == null) {
            classroom.setAssignments(assignment.get_id());
        } else {
            classroom.setAssignments(classroom.getAssignments() + "," + assignment.get_id());
        }
        classroomRepository.save(classroom);
        AssignmentV2 assignmentV2 = new AssignmentV2();
        assignmentV2.set_id(assignment.get_id());
        assignmentV2.setName(assignment.getName());
        assignmentV2.setPoint(assignment.getPoint());
        assignmentV2.setCreated_at(assignment.getCreatedAt());
        assignmentV2.setUpdate_at(assignment.getUpdateAt());
        assignmentV2.setGrades(new User[0]);

        return ResponseEntity.ok(new AssignmentResponeV2(200, true,"Assignment added successfully", assignmentV2));
    }
    @GetMapping({"/{slug}/assignment"})
    public ResponseEntity<?> showAssignment(@PathVariable String slug, @CurrentUser UserPrincipal userPrincipal) {
        Classroom classroom = classroomRepository.findBySlug(slug);
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorized"));
        }
        String[] strAssignments = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];

        Assignment[] assignments = new Assignment[strAssignments.length];
        AssignmentV2[] assignmentsV2= new AssignmentV2[strAssignments.length];
        for (int i = 0; i < strAssignments.length; i++) {
            assignmentsV2[i]=new AssignmentV2();
            assignments[i]=assignmentRepository.findBy_id(strAssignments[i]);
            if(assignments[i].getGrades()==null){
                assignmentsV2[i].setGrades(new User[0]);
            }
            assignmentsV2[i].set_id(assignments[i].get_id());
            assignmentsV2[i].setName(assignments[i].getName());
            assignmentsV2[i].setPoint(assignments[i].getPoint());
            assignmentsV2[i].setCreated_at(assignments[i].getCreatedAt());
            assignmentsV2[i].setUpdate_at(assignments[i].getUpdateAt());


        }
        return ResponseEntity.ok(new AssignmentResponeV2(200, true,assignmentsV2));
    }
    @PostMapping({"/{slug}/studentid"})
    public ResponseEntity<?>setCourseStudentIds(@PathVariable String slug,@Valid @RequestBody ClassroomRequest classroomRequest ){
        Classroom classroom = classroomRepository.findBySlug(slug);
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(classroom.getOwner())) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorized"));
        }
        if(classroomRequest.getStudentIds()==null){
            classroom.setStudentsIds("");
        }else{
            for(int i=0;i<classroomRequest.getStudentIds().length;i++){
                System.out.println(classroomRequest.getStudentIds()[i]);
                if(i==0){
                    classroom.setStudentsIds(classroomRequest.getStudentIds()[i]);
                }else{
                    classroom.setStudentsIds(classroom.getStudentsIds()+","+classroomRequest.getStudentIds()[i]);
                }
            }
        }
        classroomRepository.save(classroom);
        return ResponseEntity.ok(new ApiResponse(200, true, "Student Ids added successfully"));
    }
//    @PostMapping("/{slug}/assignment/{id}/grade")
//    public ResponseEntity<?>setSingleStudentGrade(@PathVariable String slug,@PathVariable String id,@Valid @RequestBody ClassroomRequest classroomRequest,@CurrentUser UserPrincipal userPrincipal){
//        Classroom classroom = classroomRepository.findBySlug(slug);
//        if (classroom == null) {
//            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
//        }
//        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {
//            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorized"));
//        }
//        Assignment assignment = assignmentRepository.findBy_id(id);
//        if(assignment==null){
//            return ResponseEntity.ok(new ApiResponse(200, false, "Assignment not found"));
//        }
//        if(assignment.getGrades()==null){
//            assignment.setGrades(userPrincipal.get_id()+":"+classroomRequest.getGrade());
//        }else{
//            String[] strGrades = assignment.getGrades() != null
//                    ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
//                    : new String[0];
//            for(int i=0;i<strGrades.length;i++){
//                String[] strGrade = strGrades[i].split(":");
//                if(strGrade[0].equals(userPrincipal.get_id())){
//                    strGrades[i]=userPrincipal.get_id()+":"+gradeRequest.getGrade();
//                    break;
//                }
//                if(i==strGrades.length-1){
//                    assignment.setGrades(assignment.getGrades()+","+userPrincipal.get_id()+":"+gradeRequest.getGrade());
//                }
//            }
//        }
//        assignmentRepository.save(assignment);
//        return ResponseEntity.ok(new ApiResponse(200, true, "Grade added successfully"));
//    }
}
