-- 1. Users Table
CREATE TABLE users
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    system_role VARCHAR(50)  NOT NULL DEFAULT 'EMPLOYEE',
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  DATE                  DEFAULT CURRENT_DATE
);

-- 2. Department Table
CREATE TABLE department
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- 3. Employee Table (Depends on Users, Department, and itself)
CREATE TABLE employee
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id         BIGINT UNIQUE,
    department_id   BIGINT,
    manager_id      BIGINT,
    job_title       VARCHAR(255),
    first_name      VARCHAR(100),
    last_name       VARCHAR(100),
    phone           VARCHAR(50),
    address         VARCHAR(255),
    hire_date       DATE,
    employment_type VARCHAR(50),

    CONSTRAINT fk_employee_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL,
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE SET NULL,
    CONSTRAINT fk_employee_manager FOREIGN KEY (manager_id) REFERENCES employee (id) ON DELETE SET NULL
);

-- 4. Employee Document Table (Depends on Employee)
CREATE TABLE employee_document
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id BIGINT       NOT NULL,
    doc_type    VARCHAR(50)  NOT NULL,
    file_url    VARCHAR(500) NOT NULL,
    uploaded_at DATE             DEFAULT CURRENT_DATE,

    CONSTRAINT fk_document_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE
);

-- 5. Leave Balance Table (Depends on Employee)
CREATE TABLE leave_balance
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    employee_id   BIGINT      NOT NULL,
    leave_type    VARCHAR(50) NOT NULL,
    total_allowed INT         NOT NULL,
    used_days     INT         NOT NULL DEFAULT 0,

    CONSTRAINT fk_balance_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE,
    CONSTRAINT uq_employee_leave_type UNIQUE (employee_id, leave_type)
);

-- 6. Leave Request Table (Depends on Employee)
CREATE TABLE leave_request
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    employee_id       BIGINT       NOT NULL,
    leave_type        VARCHAR(50)  NOT NULL,
    start_date        DATE         NOT NULL,
    end_date          DATE         NOT NULL,
    reason            VARCHAR(500) NOT NULL,
    leave_status      VARCHAR(50)  NOT NULL,
    reviewer_id       BIGINT,
    reviewer_comments VARCHAR(500),
    created_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP,

    CONSTRAINT fk_request_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE,
    CONSTRAINT fk_request_reviewer FOREIGN KEY (reviewer_id) REFERENCES employee (id) ON DELETE SET NULL
);

-- 7. Attendance Record Table (Depends on Employee)
CREATE TABLE attendance_record
(
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    employee_id    BIGINT      NOT NULL,
    date           DATE        NOT NULL,
    check_in_time  TIMESTAMP   NOT NULL,
    check_out_time TIMESTAMP,
    status         VARCHAR(50) NOT NULL,

    CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE,
    CONSTRAINT uq_employee_date UNIQUE (employee_id, date)
);

-- 8. Asset Table (Depends on Employee)
CREATE TABLE asset
(
    id                   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name                 VARCHAR(120) NOT NULL,
    asset_tag            VARCHAR(80)  NOT NULL UNIQUE,
    status               VARCHAR(50)  NOT NULL,
    assigned_employee_id BIGINT,
    created_at           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP,

    CONSTRAINT fk_asset_employee FOREIGN KEY (assigned_employee_id) REFERENCES employee (id) ON DELETE SET NULL
);