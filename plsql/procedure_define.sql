create or replace package body student_reg_package as
    
    -- display courses table info
    procedure show_courses_ref(cur out sys_refcursor) as
    begin
        open cur for
        select * from Courses;
    end show_courses_ref;

     -- display students table info
    procedure show_students_ref(cur out sys_refcursor) as
    begin
        open cur for
        select * from students;
    end show_students_ref;

     -- display classes table info
    procedure show_classes_ref(cur out sys_refcursor) as
    begin
        open cur for
        select * from Classes;
    end show_classes_ref;

     -- display enrollments table info
    procedure show_enrollments_ref(cur out sys_refcursor) as
    begin
        open cur for
        select * from Enrollments;
    end show_enrollments_ref;

     -- display tas table info
    procedure show_tas_ref(cur out sys_refcursor) as
    begin
        open cur for
        select * from TAs;
    end show_tas_ref;

     -- display prerequisites table info
    procedure show_prerequisites_ref(cur out sys_refcursor) as
    begin
        open cur for
        select * from Prerequisites;
    end show_prerequisites_ref;

    -- display logs table info
    procedure show_logs_ref(cur out sys_refcursor) as
    begin
        open cur for
        select * from Logs;
    end show_logs_ref;

    -- Procedure to get TA information
    procedure getTAInfo_ref(classid_val in classes.classid%type,message out varchar2,cur out sys_refcursor) is
    ta_count number;
    class_id_count number;
    begin
        select count(*) into class_id_count from classes c where classid=classid_val;
        select count(c.ta_b#) into ta_count from classes c where c.classid=classid_val and c.ta_b# is not null;  

        if class_id_count=0  THEN
            message :='The class id is invalid';
        else
            if ta_count=0 THEN
            message :='The class has no TA';
            else
                open cur for
                select s.b#,s.first_name,s.last_name from students s ,classes c where c.classid=classid_val and 
                s.b#=c.ta_b#;
            end if;            
        end if;    

    end getTAInfo_ref;


    -- Procedure to Get Prerequisites Courses
    procedure getPreRequisiteCourses_ref (dept_codeV in prerequisites.dept_code%type, course#V in prerequisites.course#%type,
    message out varchar2,cur out sys_refcursor) is
    cnt number;
    begin
     select count(*) into cnt from prerequisites where dept_code=dept_codeV and course#=course#V;
     if (cnt=0) then
        message:='dept_code || course# does not exist.';
     else
        open cur for 
        select pre_dept_code||pre_course# as pre_course from prerequisites start with dept_code=dept_codeV and course#=course#V 
        connect by prior pre_dept_code=dept_code and prior pre_course#=course# ;

    end if;
    end getPreRequisiteCourses_ref;


    -- Procedure to insert student enrollment
    procedure insert_student_enrollment_ref(b#Val in students.b#%type, classidVal in classes.classid%type,message out varchar2) is

    dept_codeV classes.dept_code%type;
    course#V classes.course#%type;
    seat_available number(4);
    cnt number (4);
    status boolean;
    student_grade_less_C number(4);
    student_pre_req number(4);
    total_pre_req number(4);
    bno_cn number;
    classid_cn number;
    classid_cn_curr number;

    begin
    status:= true;
    message:='';
    -- check whether b# is valid    
    select count(*) into bno_cn from students where b#=b#Val;
    if bno_cn=0 then
        message:='The b# is invalid';
        status:=false;
    else  
        -- check whether classid is valid     
        select count(*) into classid_cn from classes where classid= classidVal;
        if (classid_cn=0) then
            message:='The classid is invalid';
            status := false;
        else
            --check whether the class id is offerred in the current semester
            select count(*) into classid_cn_curr from classes where classid=classidVal and upper(semester)='FALL' and year=2018;
            if (classid_cn_curr=0) then
                message:='Cannot enroll into a class from a previous semester.';
                status := false;

            else
                select c.limit-c.class_size as "seat_available" into seat_available from classes c where c.classid=classidVal;
                if seat_available=0 then
                   message:='The class is already full';
                   status := false;

                else
                     select count(*) into cnt from enrollments where b#=b#Val and classid=classidVal;
                     if(cnt>0) then
                        message:='The student is already in the class.';
                        status := false;
                     else
                        select count(*) into cnt from enrollments e, classes c where e.classid=c.classid
                        and e.b#=b#Val and upper(c.semester)='FALL' and c.year=2018;

                        if (cnt<=3) then
                           status:=true;
                        end if;   

                        if(cnt=4) then
                        -- insert into enrollments
                            message:='The student will be overloaded with the new enrollment';
                            dbms_output.put_line('The student will be overloaded with the new enrollment');
                            status :=true;
                        end if;

                        if(cnt>=5) then
                            message :='Students cannot be enrolled in more than five classes in the same semester.';
                            dbms_output.put_line('Students cannot be enrolled in more than five classes in the same semester.');
                            status:=false;
                        end if;

                        if(status=true) then

                             select count(*) into total_pre_req
                             from prerequisites p,classes c where c.classid=classidVal
                             and c.dept_code=p.dept_code and c.course#=p.course#;

                            select count(*) into student_pre_req from enrollments e,classes c,
                            (select p.pre_dept_code as pre_dept_code, p.pre_course# as pre_course#
                             from prerequisites p,classes c where c.classid=classidVal
                             and c.dept_code=p.dept_code and c.course#=p.course#)t 
                             where e.b#=b#Val and e.classid=c.classid and c.dept_code=t.pre_dept_code
                             and c.course#=t.pre_course#;

                            -- First we check whether student has taken all pre_requisite courses for the given course  
                            if(student_pre_req<total_pre_req) then  
                                message:='Student has not taken all pre requisite courses to be eligible for the course.';
                                status :=false;                                  
                            else
                                 -- If student has taken all prerequisite courses make sure the scored at least C 
                                -- at every courses

                                 select count(*) into student_grade_less_C  from enrollments e, classes c, (select p.pre_dept_code as pre_dept_code,p.pre_course# as pre_course#
                                 from prerequisites p,classes c where c.classid=classidVal
                                 and c.dept_code=p.dept_code and c.course#=p.course#)t
                                 where e.b#=b#Val 
                                 and  (e.lgrade like 'D' or e.lgrade is null or e.lgrade like 'E' or 
                                 e.lgrade like 'C-' or e.lgrade like 'F' ) and e.classid=c.classid and c.dept_code=t.pre_dept_code and c.course#=t.pre_course#;

                                if student_grade_less_C>0 then 
                                    message:='Prerequisite not satisfied.';
                                    dbms_output.put_line('Prerequisite not satisfied.');
                                    status:=false;
                                else
                                    insert into enrollments values(b#Val,classidVal, NULL);
                                    commit;
                                    message:=  b#Val || ' Student Enrollment for class ' || classidVal || ' inserted successfully' || ' ' ||  message;

                                end if;
                            end if;
                       end if; 
                    end if;
                end if;
            end if;
        end if;   
    end if;    

end insert_student_enrollment_ref;




-- Procedure to drop student Enrollment
procedure drop_student_enrollment_ref(b#Val in students.b#%type, classidVal in classes.classid%type, message out varchar2) is

    status boolean;
    number_of_pre_req_classid number;
    student_enrollment_cnt number;
    b#cn number;
    classidcn number;
    classid_curr_sem number;


    class_size_val classes.class_size%type;
    no_of_class number(4);
    begin

        -- check whether b# is valid
        select count(*) into b#cn from students where b#=b#Val;
            if (b#cn=0) then
                message:= 'The B# is invalid.';
     
            else
                 -- Check whether classid is invalid
                  select count(*) into classidcn from classes where classid = classidVal;
                  if(classidcn=0) then
                          message:= 'The class id is invalid.';
     
                  else
                      -- Check whether student is enrolled in the class
                      select count(*) into student_enrollment_cnt from enrollments where b#=b#Val and classid=classidVal;
                      if(student_enrollment_cnt=0) then
                            message:= 'The student is not enrolled in the class.';
     
                      else
                          -- Check whether enrollment is in the current semester
                           select count(*) into classid_curr_sem from classes where classid=classidVal and upper(semester)='FALL' and year=2018;
                           if (classid_curr_sem=0)  then
                            --    status := false;
                                message:='Only enrollment in the current semester can be dropped.';
     
                           else
                                  -- number of total pre_requisite classid list for a given classid 
                                  select count(*) into number_of_pre_req_classid from enrollments e where e.b#=b#Val
                                  and  e.classid in
                                  (select c1.classid as classid from classes c1,
                                  (select p.dept_code as dept_code,p.course# as course#
                                  from prerequisites p,classes c where c.classid=classidVal
                                  and c.dept_code=p.pre_dept_code and c.course#=p.pre_course#)t
                                  where c1.dept_code=t.dept_code and
                                  c1.course#=t.course#);

                                  if(number_of_pre_req_classid>0)then

                                       message:='The drop is not permitted because another class the student registered uses it as a prerequisite.';
                                  else
                                           delete from enrollments where b#=b#Val and classid=classidVal;
                                           message:= b#Val || ' Student Enrollment for class ' || classidVal || ' dropped successfully';
                                           commit;

                                          select count(*) into no_of_class from enrollments where b#=b#Val;
                                          if(no_of_class=0) then
                                                message:= message|| ' ' || 'This student is not enrolled in any classes.';
                                          end if;


                                          select class_size into class_size_val from classes where classid=classidVal;
                                          if(class_size_val=0)then 
                                                message:= message||' '||' The class now has no students.';
                                          end if;

                                end if;
                           end if;
                      end if;
                  end if;
            end if;


     end drop_student_enrollment_ref;   



    -- Procedure to delete a student from the students table
    procedure delete_student_ref(b#Val in students.b#%type, message out varchar2) is
    b#cn number;    
    begin
        -- check whether b# is valid
        select count(*) into b#cn from students where b#=b#Val;
            if (b#cn=0) then
                message:= 'The B# is invalid.';
                dbms_output.put_line('The B# is invalid.');

            else
                -- delete student from the students table
                delete from students where b#=b#Val ;
                commit;
                message:= b#Val || ' Student Deleted Successfully';
            end if;


     end delete_student_ref;   


end ;