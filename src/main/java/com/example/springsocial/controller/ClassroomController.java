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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssignmentV2Repository assignmentV2Repository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private GradeReviewRepository gradeReviewRepository;
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
    //design pattern singleton

    @Autowired
    RandomStringSingleton randomStringSingleton = RandomStringSingleton.getInstance();


    private static void printVariableType(Object variable) {
        // Get the runtime type of the variable
        Class<?> variableType = variable.getClass();

        // Print the variable type
        System.out.println("Variable type: " + variableType.getName());
    }

    //done 1
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
                //design pattern facade
                this.courses[i].setOwner(customUserDetailsService.loadUserBy_id(classroom[i].getOwner()));

                User[] userTeachers = new User[strTeachers.length];

                for (int j = 0; j < strTeachers.length; j++) {
                    //design pattern facade
                    userTeachers[j] = customUserDetailsService.loadUserBy_id(strTeachers[j]);
                }
                if (strTeachers.length != 0) {
                    this.courses[i].setTeachers(strTeachers);
                }
                if (strStudents.length != 0) {
                    this.courses[i].setStudents(strStudents);
                }
                if (strStudentsIds.length != 0) {
                    this.courses[i].setStudentIds(strStudentsIds);
                }
                if (strAssignments.length != 0) {
                    this.courses[i].setAssignments(strAssignments);
                }
                this.courses[i].set_id(classroom[i].get_id());
                this.courses[i].setName(classroom[i].getName());
                this.courses[i].setDescription(classroom[i].getDescription());
                this.courses[i].setSlug(classroom[i].getSlug());
                this.courses[i].setJoinId(classroom[i].getJoinId());
                this.courses[i].setCreatedAt(classroom[i].getCreatedAt());
                this.courses[i].setUpdateAt(classroom[i].getUpdateAt());
            }
            return ResponseEntity.ok(new ApiClassroomResponse(true, 200, courses));
        }
    }

    //done 2
    @PostMapping("/store")
    public ResponseEntity<?> createClassroom(@Valid @RequestBody ClassroomRequest classroomRequest, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
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

        classroomRepository.save(classroom);
        //design pattern:prototype
        Classroom result = classroom.clone();

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

    //done 3
    @GetMapping("/{slug}")
    public ResponseEntity<?> showOneClassroom(@PathVariable String slug) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        Grade[] grades = new Grade[0];

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
            for (int i = 0; i < strStudentsIds.length; i++) {
                System.out.println(strStudentsIds[i]);
            }
            User[] userTeachers = new User[strTeachers.length];
            User[] userStudents = new User[strStudents.length];
            User[] userStudentsIds = new User[strStudentsIds.length];
            //design pattern facade
            User owner = customUserDetailsService.loadUserBy_id(classroom.getOwner());

            for (int i = 0; i < strTeachers.length; i++) {
                //design pattern facade
                userTeachers[i] = customUserDetailsService.loadUserBy_id(strTeachers[i]);
            }

            for (int i = 0; i < strStudents.length; i++) {
                //design pattern facade
                userStudents[i] = customUserDetailsService.loadUserBy_id(strStudents[i]);
            }


            this.course.setTeachers(userTeachers);
            this.course.setStudents(userStudents);
            if (strStudentsIds.length != 0) {
                this.course.setStudentIds(strStudentsIds);
            } else if (strStudentsIds.length == 0) {
                this.course.setStudentIds(new User[0]);
            }
            this.course.setOwner(owner);
            this.course.set_id(classroom.get_id());
            this.course.setName(classroom.getName());
            this.course.setDescription(classroom.getDescription());
            this.course.setSlug(classroom.getSlug());
            this.course.setJoinId(classroom.getJoinId());
            this.course.setCreatedAt(classroom.getCreatedAt());
            this.course.setUpdateAt(classroom.getUpdateAt());
            Assignment[] assignments = new Assignment[strAssignments.length];
            AssignmentV2[] assignmentsV2 = new AssignmentV2[strAssignments.length];
            for (int i = 0; i < strAssignments.length; i++) {

                assignmentsV2[i] = new AssignmentV2();
                assignments[i] = assignmentRepository.findBy_id(strAssignments[i]);
                if (assignments[i] != null) {
                    if (assignments[i].getGrades() == null) {
                        assignmentsV2[i].setGrades(new User[0]);
                    } else {
                        String[] strGrade = assignments[i].getGrades() != null
                                ? convertStringToArrayList.convertToArrayList(assignments[i].getGrades()).toArray(new String[0])
                                : new String[0];

                        grades = new Grade[strGrade.length];
                        for (int j = 0; j < strGrade.length; j++) {

                            grades[j] = gradeRepository.findBy_id(strGrade[j]);
                        }
                        GradeV2[] gradesV2 = new GradeV2[grades.length];
                        for (int k = 0; k < grades.length; k++) {
                            gradesV2[k] = new GradeV2();
                            gradesV2[k].setGradeid(grades[k].getId());
                            gradesV2[k].setId(grades[k].getStudentId());
                            gradesV2[k].set_id(grades[k].get_id());
                            gradesV2[k].setDraft(grades[k].isDraft());
                            gradesV2[k].setGrade(grades[k].getGrade());
                        }
                        assignmentsV2[i].setGrades(gradesV2);
                    }
                    assignmentsV2[i].set_id(assignments[i].get_id());
                    assignmentsV2[i].setName(assignments[i].getName());
                    assignmentsV2[i].setPoint(assignments[i].getPoint());
                    assignmentsV2[i].setCreated_at(assignments[i].getCreatedAt());
                    assignmentsV2[i].setUpdate_at(assignments[i].getUpdateAt());

                }

            }
            if (assignmentsV2.length != 0)
                this.course.setAssignments(assignmentsV2);
            else if (assignmentsV2.length == 0) {

                this.course.setAssignments(new AssignmentV2[0]);
            }


            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .buildAndExpand(classroom.get_id()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiClassroomResponse(true, 200, course));
        }


    }

    //done 4
    @GetMapping({"/{classId}/invitation"})
    public ResponseEntity<?> ShowClassroomCode(@PathVariable String classId) throws CloneNotSupportedException {
        Invitation invitation1 = invitationRepository.findByCourseId(classId);
        Invitation invitation = invitation1.clone();
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(invitation.get_id()).toUri();
        if (invitation == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "No class found"));
        } else {
            return ResponseEntity.created(location).body(new InvitationRespone(true, 200, invitation));
        }
    }

    //done 5
    @GetMapping({"/join/{id}"})
    public ResponseEntity<?> joinCourse(@PathVariable String id, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
        //design pattern facade
        User user = customUserDetailsService.loadUserBy_id(userPrincipal.get_id());
        Invitation invitation1 = invitationRepository.findByInviteCode(id);
        Invitation invitation = invitation1.clone();

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
            //design pattern facade
            userTeachers[i] = customUserDetailsService.loadUserBy_id(strTeachers[i]);
        }

        for (int i = 0; i < strStudents.length; i++) {
            //design pattern facade
            userStudents[i] = customUserDetailsService.loadUserBy_id(strStudents[i]);
        }

        for (int i = 0; i < strStudentsIds.length; i++) {
            //design pattern facade
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

    //done 7
    @PostMapping({"/{slug}/assignment"})
    public ResponseEntity<?> createAssignment(@PathVariable String slug, @Valid @RequestBody AssignmentRequest assignmentRequest, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        String randomGradeId = randomStringSingleton.generateRandomString(24);

        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {

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


        return ResponseEntity.ok(new AssignmentResponeV2(200, true, "Assignment added successfully", assignmentV2));
    }

    //done 6
    @GetMapping({"/{slug}/assignment"})
    public ResponseEntity<?> showAssignment(@PathVariable String slug, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
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
        AssignmentV2[] assignmentsV2 = new AssignmentV2[strAssignments.length];
        for (int i = 0; i < strAssignments.length; i++) {
            assignmentsV2[i] = new AssignmentV2();
            assignments[i] = assignmentRepository.findBy_id(strAssignments[i]);
            if (assignments[i] == null) {

                continue;
            }
            if (assignments[i].getGrades() == null) {
                assignmentsV2[i].setGrades(new User[0]);
            }
            assignmentsV2[i].set_id(assignments[i].get_id());
            assignmentsV2[i].setName(assignments[i].getName());
            assignmentsV2[i].setPoint(assignments[i].getPoint());
            assignmentsV2[i].setCreated_at(assignments[i].getCreatedAt());
            assignmentsV2[i].setUpdate_at(assignments[i].getUpdateAt());


        }
        return ResponseEntity.ok(new AssignmentResponeV2(200, true, assignmentsV2));
    }

    //done 8
    @PostMapping({"/{slug}/studentid"})
    public ResponseEntity<?> setCourseStudentIds(@PathVariable String slug, @Valid @RequestBody ClassroomRequest classroomRequest) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(classroom.getOwner())) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorized"));
        }
        if (classroomRequest.getStudentIds() == null) {
            classroom.setStudentsIds("");
        } else {
            for (int i = 0; i < classroomRequest.getStudentIds().length; i++) {
                System.out.println(classroomRequest.getStudentIds()[i]);
                if (i == 0) {
                    classroom.setStudentsIds(classroomRequest.getStudentIds()[i]);
                } else {
                    classroom.setStudentsIds(classroom.getStudentsIds() + "," + classroomRequest.getStudentIds()[i]);
                }
            }
        }
        classroomRepository.save(classroom);
        return ResponseEntity.ok(new ApiResponse(200, true, "Student Ids added successfully"));
    }

    //done 10
    @PostMapping("/{slug}/assignment/{id}/grade")
    public ResponseEntity<?> setSingleStudentGrade(@PathVariable String slug, @PathVariable String id, @Valid @RequestBody ClassroomRequest classroomRequest, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        String randomGradeId = randomStringSingleton.generateRandomString(24);
        Assignment assignment = new Assignment();
        Grade checkGradeExist = new Grade();

        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorize"));
        }

        String[] strAssignment = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];


        for (int i = 0; i < strAssignment.length; i++) {
            if (strAssignment[i].equals(id)) {
                assignment = assignmentRepository.findBy_id(strAssignment[i]);
                String[] strGrade = assignment.getGrades() != null
                        ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
                        : new String[0];


                if (strGrade.length == 0) {
                    assignment.setGrades(randomGradeId);
                    assignmentRepository.save(assignment);
                } else {
                    for (int j = 0; j < strGrade.length; j++) {
                        checkGradeExist = gradeRepository.findBy_id(strGrade[j]);
                        if (checkGradeExist.getStudentId().equals(classroomRequest.getStudentId())) {
                            checkGradeExist.setGrade(classroomRequest.getGrade());
                            checkGradeExist.setDraft(true);
                            gradeRepository.save(checkGradeExist);
                            return ResponseEntity.ok(new ApiResponse(200, true, "Grade updated successfully"));
                        }
                    }


                    assignment.setGrades(assignment.getGrades() + "," + randomGradeId);
                    assignmentRepository.save(assignment);

                }

                Grade grade = new Grade();
                grade.set_id(randomGradeId);
                grade.setGrade(classroomRequest.getGrade());
                grade.setDraft(true);
                grade.setStudentId(classroomRequest.getStudentId());
                gradeRepository.save(grade);
                return ResponseEntity.ok(new ApiResponse(200, true, "Grade added successfully"));

            }
        }


        return ResponseEntity.ok(new ApiResponse(200, false, "Assignment not found"));
    }

    //done 9
    @GetMapping("/{slug}/assignment/{id}/grade")
    public ResponseEntity<?> getSingleStudentGrade(@PathVariable String slug, @PathVariable String id, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        Assignment assignment = new Assignment();
        Grade[] grades = new Grade[0];
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorize"));
        }
        String[] strAssignment = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];
        for (int i = 0; i < strAssignment.length; i++) {
            if (strAssignment[i].equals(id)) {
                assignment = assignmentRepository.findBy_id(strAssignment[i]);

                String[] strGrade = assignment.getGrades() != null
                        ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
                        : new String[0];

                grades = new Grade[strGrade.length];
                for (int j = 0; j < strGrade.length; j++) {

                    grades[j] = gradeRepository.findBy_id(strGrade[j]);
                }
            }
        }
        GradeV2[] gradesV2 = new GradeV2[grades.length];
        for (int i = 0; i < grades.length; i++) {
            gradesV2[i] = new GradeV2();
            gradesV2[i].setGradeid(grades[i].getId());
            gradesV2[i].setId(grades[i].getStudentId());
            gradesV2[i].set_id(grades[i].get_id());
            gradesV2[i].setDraft(grades[i].isDraft());
            gradesV2[i].setGrade(grades[i].getGrade());
        }

        return ResponseEntity.ok(new GradeResponse(200, true, "Grade found", gradesV2));

    }

    //done 11
    @PostMapping("/{slug}/assignment/{id}/finalize")
    public ResponseEntity<?> setSingleGradeFinalize(@PathVariable String slug, @PathVariable String id, @Valid @RequestBody ClassroomRequest classroomRequest, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        Assignment assignment = new Assignment();
        Grade[] grades = new Grade[0];
        Grade grade = new Grade();

        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorize"));
        }
        String[] strAssignment = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];
        for (int i = 0; i < strAssignment.length; i++) {
            if (strAssignment[i].equals(id)) {
                assignment = assignmentRepository.findBy_id(strAssignment[i]);
                String[] strGrade = assignment.getGrades() != null
                        ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
                        : new String[0];
                for (int j = 0; j < strGrade.length; j++) {
                    grade = gradeRepository.findBy_id(strGrade[j]);
                    if (grade.getStudentId().equals(classroomRequest.getStudentId())) {
                        grade.setDraft(false);
                        gradeRepository.save(grade);
                    }
                }

            }
        }
        return ResponseEntity.ok(new ApiResponse(200, true, "Finalize grade set successfully"));
    }

    //done 12
    @GetMapping("/{slug}/assignment/{id}/review")
    public ResponseEntity<?> getGradeReviews(@PathVariable String slug, @PathVariable String id) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        Assignment assignment = new Assignment();
        Grade[] grades = new Grade[0];
        Grade grade = new Grade();
        GradeReview[] gradeReviews = new GradeReview[1000];

        int sizeGradeReview = 0;

        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }

        String[] strAssignment = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];
        for (int i = 0; i < strAssignment.length; i++) {
            if (strAssignment[i].equals(id)) {
                assignment = assignmentRepository.findBy_id(strAssignment[i]);
                if (gradeReviewRepository.findByAssignmentId(id) == null) {
                    continue;
                } else {
                    gradeReviews[sizeGradeReview] = gradeReviewRepository.findByAssignmentId(id);
                    sizeGradeReview++;
                }
            }
        }
        GradeReview[] result = new GradeReview[sizeGradeReview];
        GradeReviewV2[] gradeReviewV2s = new GradeReviewV2[sizeGradeReview];

        for (int i = 0; i < sizeGradeReview; i++) {
            gradeReviewV2s[i] = new GradeReviewV2();
            gradeReviewV2s[i].setId(gradeReviews[i].getId());
            gradeReviewV2s[i].set_id(gradeReviews[i].get_id());
            gradeReviewV2s[i].setStudentId(gradeReviews[i].getStudentId());
            gradeReviewV2s[i].setAssignmentId(gradeReviews[i].getAssignmentId());
            gradeReviewV2s[i].setExpectedGrade(gradeReviews[i].getExpectedGrade());
            gradeReviewV2s[i].setActualGrade(gradeReviews[i].getActualGrade());
            gradeReviewV2s[i].setMessage(gradeReviews[i].getMessage());
            gradeReviewV2s[i].setStatus(gradeReviews[i].getStatus());
            gradeReviewV2s[i].setCreatedAt(gradeReviews[i].getCreatedAt());
            gradeReviewV2s[i].setUpdatedAt(gradeReviews[i].getUpdatedAt());
            if (gradeReviews[i].getComments() != null) {
                String[] strComments = gradeReviews[i].getComments() != null
                        ? convertStringToArrayList.convertToArrayList(gradeReviews[i].getComments()).toArray(new String[0])
                        : new String[0];
                gradeReviewV2s[i].setComments(strComments);
            } else {
                gradeReviewV2s[i].setComments(new String[0]);
            }
        }
        GradeReviewV3[] gradeReviewV3s = new GradeReviewV3[gradeReviewV2s.length];
        for (int i = 0; i < gradeReviewV2s.length; i++) {
            gradeReviewV3s[i] = new GradeReviewV3();
            gradeReviewV3s[i].setId(gradeReviewV2s[i].getId());
            gradeReviewV3s[i].set_id(gradeReviewV2s[i].get_id());
            gradeReviewV3s[i].setStudentId(gradeReviewV2s[i].getStudentId());
            gradeReviewV3s[i].setAssignmentId(gradeReviewV2s[i].getAssignmentId());
            gradeReviewV3s[i].setExpectedGrade(gradeReviewV2s[i].getExpectedGrade());
            gradeReviewV3s[i].setActualGrade(gradeReviewV2s[i].getActualGrade());
            gradeReviewV3s[i].setMessage(gradeReviewV2s[i].getMessage());
            gradeReviewV3s[i].setStatus(gradeReviewV2s[i].getStatus());
            gradeReviewV3s[i].setCreatedAt(gradeReviewV2s[i].getCreatedAt());
            gradeReviewV3s[i].setUpdatedAt(gradeReviewV2s[i].getUpdatedAt());
            if (gradeReviewV2s[i].getComments().length == 0) {
                System.out.println("NULLLLLLLLLLLLL");
                gradeReviewV3s[i].setComments(new Comment[0]);
            } else {
                System.out.println("NOTTTTTTTTTTTTT");
                System.out.println("LENGTH" + gradeReviewV2s[i].getComments().length);
                Comment[] comments = new Comment[gradeReviewV2s[i].getComments().length];

                for (int j = 0; j < gradeReviewV2s[i].getComments().length; j++) {
                    System.out.println("COMment" + gradeReviewV2s[i].getComments());
                    Comment comment = commentRepository.findBy_id(gradeReviewV2s[i].getComments()[j]);
                    comments[j] = comment;
                    gradeReviewV3s[i].setComments(comments);
                }
            }


        }
        return ResponseEntity.ok(new GradeReviewResponeV2(true, 200, gradeReviewV3s));

    }

    //done 13
    @PostMapping("/{slug}/assignment/{id}/review")
    public ResponseEntity<?> submitGradeReview(@PathVariable String slug, @PathVariable String id, @CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody ReviewRequest reviewRequest) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        //design pattern facade
        User user = customUserDetailsService.loadUserBy_id(userPrincipal.get_id());
        Assignment assignment = new Assignment();
        Grade[] grades = new Grade[0];
        Grade grade = new Grade();
        GradeReview gradeReview = new GradeReview();
        String randomGradeReviewId = randomStringSingleton.generateRandomString(24);

        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }

        String[] strAssignment = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];
        for (int i = 0; i < strAssignment.length; i++) {
            if (strAssignment[i].equals(id)) {
                assignment = assignmentRepository.findBy_id(strAssignment[i]);
                String[] strGrade = assignment.getGrades() != null
                        ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
                        : new String[0];

                for (int j = 0; j < strGrade.length; j++) {
                    grade = gradeRepository.findBy_id(strGrade[j]);
                    if (grade.getStudentId().equals(user.getStudent())) {
                        System.out.println("MESSAGEEEE" + reviewRequest.getMessage());
                        gradeReview.set_id(randomGradeReviewId);
                        gradeReview.setStudentId(user.getStudent());
                        gradeReview.setAssignmentId(id);
                        gradeReview.setExpectedGrade(reviewRequest.getExpectedGrade());
                        gradeReview.setActualGrade(grade.getGrade());
                        gradeReview.setMessage(reviewRequest.getMessage());
                        gradeReview.setStatus(0);
                        gradeReview.setComments(null);
                        gradeReview.setCreatedAt(LocalDate.now());
                        gradeReview.setUpdatedAt(LocalDate.now());
                        gradeReviewRepository.save(gradeReview);
                        return ResponseEntity.ok(new ApiResponse(200, true, "Grade review submitted successfully"));

                    }
                }

            }

        }
        return ResponseEntity.ok(new ApiResponse(200, false, "Not found assignment"));

    }

    //done 14
    @PostMapping("/{slug}/assignment/{id}/review/{reviewId}/finalize")
    public ResponseEntity<?> makeFinalReview(@PathVariable String slug, @PathVariable String id, @PathVariable String reviewId, @CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody ReviewRequest reviewRequest) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        //design pattern facade
        User user = customUserDetailsService.loadUserBy_id(userPrincipal.get_id());
        Assignment assignment = new Assignment();
        Grade grade = new Grade();
        GradeReview gradeReview = new GradeReview();
        String randomGradeReviewId = randomStringSingleton.generateRandomString(24);
        GradeReview gradeReview1 = gradeReviewRepository.findBy_id(reviewId);

        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }

        String[] strAssignment = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];

        for (int i = 0; i < strAssignment.length; i++) {
            if (strAssignment[i].equals(id)) {
                assignment = assignmentRepository.findBy_id(strAssignment[i]);
                String[] strGrade = assignment.getGrades() != null
                        ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
                        : new String[0];

                for (int j = 0; j < strGrade.length; j++) {
                    grade = gradeRepository.findBy_id(strGrade[j]);
                    if (grade.getStudentId().equals(gradeReview1.getStudentId())) {
                        System.out.println(reviewRequest.getApprove());
                        System.out.println(reviewRequest.getGrade());
                        if (reviewRequest.getApprove() == false) {
                            gradeReview1.setStatus(2);
                            gradeReviewRepository.save(gradeReview1);
                            return ResponseEntity.ok(new ApiResponse(200, true, "Review marked as not approved"));
                        } else if (reviewRequest.getApprove() == true) {

                            gradeReview1.setStatus(1);
                            grade.setGrade(reviewRequest.getGrade());
                            gradeRepository.save(grade);
                            gradeReviewRepository.save(gradeReview1);
                            return ResponseEntity.ok(new ApiResponse(200, true, "Review marked as approved"));
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok(new ApiResponse(200, false, "Not found assignment"));
    }

    //done 15
    @PostMapping("/{slug}/assignment/{id}/review/{reviewId}/comment")
    public ResponseEntity<?> reviewAddComment(@PathVariable String slug, @PathVariable String id, @PathVariable String reviewId, @CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody CommentRequest commentRequest) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        //design pattern facade
        User user = customUserDetailsService.loadUserBy_id(userPrincipal.get_id());
        Assignment assignment = new Assignment();
        Grade grade = new Grade();
        GradeReview gradeReview = new GradeReview();
        String randomCommentId = randomStringSingleton.generateRandomString(24);
        GradeReview gradeReview1 = gradeReviewRepository.findBy_id(reviewId);

        Comment comment = new Comment();
        comment.set_id(randomCommentId);
        comment.setContent(commentRequest.getContent());
        comment.setCreatedAt(LocalDate.now());
        comment.setUserId(user.get_id());
        comment.setName(user.getName());
        commentRepository.save(comment);
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }

        String[] strAssignment = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];

        for (int i = 0; i < strAssignment.length; i++) {
            if (strAssignment[i].equals(id)) {
                assignment = assignmentRepository.findBy_id(strAssignment[i]);
                String[] strGrade = assignment.getGrades() != null
                        ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
                        : new String[0];
                for (int j = 0; j < strGrade.length; j++) {
                    grade = gradeRepository.findBy_id(strGrade[j]);
                    if (grade.getStudentId().equals(gradeReview1.getStudentId())) {
                        if (gradeReview1.getComments() == null) {
                            gradeReview1.setComments(randomCommentId);
                            gradeReviewRepository.save(gradeReview1);
                        } else {
                            gradeReview1.setComments(gradeReview1.getComments() + "," + randomCommentId);
                            gradeReviewRepository.save(gradeReview1);
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok(new CommentResponse(200, true, "Comment added successfully", comment));
    }

    //done 16
    @PatchMapping({"/{slug}/assignment/{id}"})
    public ResponseEntity<?> updateAssignment(@PathVariable String slug, @PathVariable String id, @Valid @RequestBody AssignmentRequest assignmentRequest, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        Grade[] grades = new Grade[0];
        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorized"));
        }
        Assignment assignment = assignmentRepository.findBy_id(id);
        if (assignment == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Assignment not found"));
        }
        assignment.setName(assignmentRequest.getName());
        assignment.setPoint(assignmentRequest.getPoint());
        assignment.setUpdateAt(LocalDate.now());
        assignmentRepository.save(assignment);
        AssignmentV2 assignmentV2 = new AssignmentV2();
        assignmentV2.set_id(assignment.get_id());
        assignmentV2.setName(assignment.getName());
        assignmentV2.setPoint(assignment.getPoint());
        assignmentV2.setCreated_at(assignment.getCreatedAt());
        assignmentV2.setUpdate_at(assignment.getUpdateAt());
        String[] strGrade = assignment.getGrades() != null
                ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
                : new String[0];
        for (int i = 0; i < strGrade.length; i++) {
            grades = new Grade[strGrade.length];
            for (int j = 0; j < strGrade.length; j++) {

                grades[j] = gradeRepository.findBy_id(strGrade[j]);
            }
        }
        GradeV2[] gradesV2 = new GradeV2[grades.length];
        for (int i = 0; i < grades.length; i++) {
            gradesV2[i] = new GradeV2();
            gradesV2[i].setGradeid(grades[i].getId());
            gradesV2[i].setId(grades[i].getStudentId());
            gradesV2[i].set_id(grades[i].get_id());
            gradesV2[i].setDraft(grades[i].isDraft());
            gradesV2[i].setGrade(grades[i].getGrade());
        }
        assignmentV2.setGrades(gradesV2);

        return ResponseEntity.ok(new AssignmentResponeV2(200, true, "Assignment updated successfully", assignmentV2));
    }

    @DeleteMapping({"/{slug}/assignment/{id}"})
    public ResponseEntity<?> deleteAssignment(@PathVariable String slug, @PathVariable String id, @CurrentUser UserPrincipal userPrincipal) throws CloneNotSupportedException {
        Classroom classroom1 = classroomRepository.findBySlug(slug);
        //design pattern:prototype
        Classroom classroom = classroom1.clone();
        GradeReview gradeReview = gradeReviewRepository.findByAssignmentId(id);

        if (classroom == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Course not found"));
        }
        if (classroom.getTeachers() != null && !Arrays.asList(classroom.getTeachers()).contains(userPrincipal.get_id())) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Unauthorized"));
        }
        Assignment assignment = assignmentRepository.findBy_id(id);
        if (assignment == null) {
            return ResponseEntity.ok(new ApiResponse(200, false, "Assignment not found"));
        }
        String[] strAssignments = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];
        String[] strGrade = assignment.getGrades() != null
                ? convertStringToArrayList.convertToArrayList(assignment.getGrades()).toArray(new String[0])
                : new String[0];

        if (gradeReview != null) {
            String[] strComment = gradeReview.getComments() != null
                    ? convertStringToArrayList.convertToArrayList(gradeReview.getComments()).toArray(new String[0])
                    : new String[0];
            ArrayList<String> commentList = new ArrayList<String>();

            for (int i = 0; i < strComment.length; i++) {
                if (!strComment[i].equals(id)) {
                    commentList.add(strComment[i]);
                }

            }
            String[] newStrComments = commentList.toArray(new String[0]);
            if (newStrComments.length == 0) {
                gradeReview.setComments(null);
                gradeReviewRepository.save(gradeReview);
            } else {
                gradeReview.setComments(String.join(",", newStrComments));
                gradeReviewRepository.save(gradeReview);
            }
            for (int i = 0; i < newStrComments.length; i++) {
                Comment comment = commentRepository.findBy_id(newStrComments[i]);
                commentRepository.delete(comment);
            }

        }


        ArrayList<String> assignmetsList = new ArrayList<String>();
        ArrayList<String> gradesList = new ArrayList<String>();

        for (int i = 0; i < strAssignments.length; i++) {
            if (!strAssignments[i].equals(id)) {
                assignmetsList.add(strAssignments[i]);
            }
        }
        for (int i = 0; i < strGrade.length; i++) {
            if (!strGrade[i].equals(id)) {
                gradesList.add(strGrade[i]);
            }
        }
        String[] newStrAssignments = assignmetsList.toArray(new String[0]);
        String[] newStrGrades = gradesList.toArray(new String[0]);
        if (newStrAssignments.length == 0) {
            classroom.setAssignments(null);
            classroomRepository.save(classroom);
        } else {
            classroom.setAssignments(String.join(",", newStrAssignments));
            classroomRepository.save(classroom);
        }
        if (newStrGrades.length == 0) {
            assignment.setGrades(null);
            assignmentRepository.save(assignment);

        } else {
            assignment.setGrades(String.join(",", newStrGrades));
            assignmentRepository.save(assignment);
        }

        for (int i = 0; i < newStrGrades.length; i++) {
            Grade grade = gradeRepository.findBy_id(newStrGrades[i]);
            gradeRepository.delete(grade);
        }
        if (gradeReview != null) {
            gradeReviewRepository.delete(gradeReview);
        }

        assignmentRepository.delete(assignment);

        return ResponseEntity.ok(new ApiResponse(200, true, "Assignment deleted successfully"));
    }


}
