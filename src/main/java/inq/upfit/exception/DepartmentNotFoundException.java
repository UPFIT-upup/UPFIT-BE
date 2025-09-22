package inq.upfit.exception;

public class DepartmentNotFoundException extends RuntimeException {
  public DepartmentNotFoundException(String message) {
    super(message);
  }

  public DepartmentNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public DepartmentNotFoundException(Long departmentId) {
    super("부서를 찾을 수 없습니다. ID: " + departmentId);
  }
}