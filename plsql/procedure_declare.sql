create or replace package student_reg_package as
procedure show_courses_ref(cur out sys_refcursor);
procedure show_students_ref(cur out sys_refcursor);
procedure show_classes_ref(cur out sys_refcursor);
procedure show_enrollments_ref(cur out sys_refcursor);
procedure show_tas_ref(cur out sys_refcursor);
procedure show_prerequisites_ref(cur out sys_refcursor);
procedure show_logs_ref(cur out sys_refcursor);
procedure getTAInfo_ref(classid_val in classes.classid%type,message out varchar2,cur out sys_refcursor);
procedure getPreRequisiteCourses_ref (dept_codeV in prerequisites.dept_code%type, course#V in prerequisites.course#%type,
message out varchar2, cur out sys_refcursor);
procedure insert_student_enrollment_ref(b#Val in students.b#%type, classidVal in classes.classid%type,message out varchar2);
procedure drop_student_enrollment_ref(b#Val in students.b#%type, classidVal in classes.classid%type, message out varchar2);
procedure delete_student_ref(b#Val in students.b#%type, message out varchar2);
end;
/