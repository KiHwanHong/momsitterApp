create table momsitter.parents
(
    parents_number                          bigint auto_increment COMMENT '회원가입시 자동으로 부여되는 고유한 번호입니다. 예) 5321' PRIMARY KEY,
    parents_name                            varchar(10)  NOT NULL DEFAULT '' COMMENT '회원의 이름입니다. 예) 박부모',
    parents_birth_day                       varchar(8)   NOT NULL COMMENT '회원의 YYYYMMDD 포맷의 생년월일입니다. 예) 19861019',
    parents_gender                          varchar(1)   NOT NULL COMMENT '회원의 성별 정보입니다. 예) 여',
    parents_id                              varchar(200) NULL DEFAULT '' COMMENT '회원이 회원가입시 제출한 고유한 아이디입니다. 예) kimParent86',
    parents_password                        varchar(255) NULL DEFAULT '' COMMENT '회원의 계정 비밀번호입니다. 예) 86!@Kim',
    parents_email                           varchar(255) NULL DEFAULT '' COMMENT '회원의 이메일 정보입니다. 예) wonderfulPark0206@gmail.com',
    parents_baby_info                       varchar(255) NULL DEFAULT '' COMMENT '부모회원이 돌봄을 원하는 아이 정보입니다. 한명의 부모는 여러명의 아이 정보를 가질 수 있습니다.',
    parents_application_contents            TEXT NULL COMMENT '신청 내용	간단한 돌봄 신청 내용입니다.	예) 하루에 2시간 정도 한글놀이를 해 줄 수 있는 시터를 찾습니다 :)'

)
    comment '부모회원 정보';

create table momsitter.members
(
    members_number                          bigint auto_increment COMMENT '회원가입시 자동으로 부여되는 고유한 번호입니다. 예) 5321'  PRIMARY KEY,
    members_name                            varchar(10)  NOT NULL DEFAULT '' COMMENT '회원의 이름입니다. 예) 박시터',
    members_birth_day                       varchar(8)   NOT NULL COMMENT '회원의 YYYYMMDD 포맷의 생년월일입니다. 예) 19980206',
    members_gender                          varchar(1)   NOT NULL COMMENT '회원의 성별 정보입니다. 예) 여',
    members_id                              varchar(200) NULL DEFAULT '' COMMENT '회원이 회원가입시 제출한 고유한 아이디입니다. 예) wonderfulPark0206',
    members_password                        varchar(255) NULL DEFAULT '' COMMENT '회원의 계정 비밀번호입니다. 예) parak0206%^',
    members_email                           varchar(255) NULL DEFAULT '' COMMENT '회원의 이메일 정보입니다. 예) wonderfulPark0206@gmail.com',
    members_care_range_info                 varchar(255) NULL DEFAULT '' COMMENT '시터회원이 케어 가능한 연령 범위 정보입니다. 예)	3세 ~ 5세회원의 이메일 정보입니다.',
    members_introduce_comments              TEXT NULL COMMENT '자기소개 간단한 자기소개 정보입니다. 예) 유아교육과를 전공중인 대학생 시터입니다! 사촌 동생들을 많이 돌본 경험이 있어서 아이랑 잘 놀아줄 수 있어요.'
)
    comment '시터회원 정보';


-- // 샘플 데이터 //

-- 부모회원 정보
-- table : momsitter.parents
INSERT INTO momsitter.parents (parents_number, parents_name, parents_birth_day, parents_gender, parents_id, parents_password, parents_email, parents_baby_info, parents_application_contents) VALUES (1, '박부모', '19861019', '여', 'kimParent86', '86!@Kim', 'wonderfulPark0206@gmail.com', '아이 정보', '하루에 2시간 정도 한글놀이를 해 줄 수 있는 시터를 찾습니다 :)');
-- 시터회원 정보
-- table : momsitter.members
INSERT INTO momsitter.members(members_number, members_name, members_birth_day, members_gender, members_id, members_password, members_email, members_care_range_info, members_introduce_comments) VALUES (1, '박시터', '19980206', '여', 'wonderfulPark0206', 'parak0206%^', 'wonderfulPark0206@gmail.com', '3세 ~ 5세회원의 이메일 정보입니다.', '유아교육과를 전공중인 대학생 시터입니다! 사촌 동생들을 많이 돌본 경험이 있어서 아이랑 잘 놀아줄 수 있어요.');
