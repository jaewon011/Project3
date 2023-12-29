DROP VIEW IF EXISTS minfo;
DROP VIEW IF EXISTS tlist;
DROP VIEW IF EXISTS HLIST;
DROP VIEW IF EXISTS trLIST;


drop table if exists member CASCADE;
drop table if exists hisboard CASCADE;
drop table if exists HISFILE CASCADE;
drop table if exists tboard CASCADE;
DROP TABLE IF EXISTS tboardfile CASCADE;
drop table if exists qboard CASCADE;
drop table if exists treply CASCADE;
drop table if exists qreply CASCADE;



create table if not exists member(
	mid VARCHAR(20) binary not NULL PRIMARY KEY,
    mpw varchar(100) not null,
    mname varchar(20) not null,
    mph varchar(20) not null,
    memail varchar(20) not null,
    mgrade varchar(20),
    mpayment varchar(30)
);

create table if not exists hisboard(
	hisnum int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    hisname varchar(20) not null,
    hisloca varchar(40) not null,
    hisexplan varchar(2000),
    hisinfo varchar(2000)
);

CREATE TABLE IF NOT EXISTS HISFILE(
    hF_NUM INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hF_hisnum INT NOT NULL,
    hF_ORINAME VARCHAR(50) NOT NULL,
    hF_SYSNAME VARCHAR(50) NOT NULL,
    CONSTRAINT FK_hF_hisnum FOREIGN KEY(hF_hisnum)
	REFERENCES hisboard(hisnum)
);

create table if not exists tboard(
	tnum int auto_increment not null primary key,
    tmid varchar(20) binary not null,
    ttitle varchar(50) not null,
    tcontents varchar(5000) not null,
    tpeople int not null,
    tstart date not null,
    tend date not null,
    tstatus varchar(10) BINARY default '대기', 
    constraint fk_tmid foreign key(tmid)
    references member(mid)
);

CREATE TABLE IF NOT EXISTS tboardfile(
    tfNUM INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tftNUM INT NOT NULL,
    toriname varchar(50),
    tsysname varchar(50),
    CONSTRAINT FK_tftNUM FOREIGN KEY(tftNUM)
	REFERENCES tboard(tnum)
);

create table if not exists qboard(
	qnum int auto_increment not null primary key,
    qmid varchar(20) binary not null,
    qtitle varchar(50) not null,
    qcategory varchar(20) not null,
    qcontents varchar(5000),
    qdate DATETIME DEFAULT CURRENT_TIMESTAMP,
    constraint fk_qmid foreign key(qmid)
    references member(mid)
);

create table if not exists treply(
	trnum int auto_increment not null primary key,
    trbnum int not null,
    trmid varchar(20) binary not null,
    trcontents varchar(200),
    trdate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_trmid foreign key(trmid)
    references member(mid),
     constraint fk_trbnum foreign key(trbnum)
    references tboard(tnum)
);

create table if not exists qreply(
	qrnum int auto_increment not null primary key,
    qrbnum int not null,
    qrmid varchar(20) binary not null,
    qrcontents varchar(200),
    qrdate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_qrmid foreign key(qrmid)
    references member(mid),
    constraint fk_qrbnum foreign key(qrbnum)
    references qboard(qnum)
);



commit;

update member set mgrade = '관리자' where mid = 'admin';

rollback;

CREATE OR REPLACE VIEW MINFO AS
SELECT mid, mpw, mname, mph, memail, mgrade, mpayment from member;

create or replace view tlist as
select tnum, 
		tmid, 
        ttitle, 
        tcontents, 
        tpeople, 
        tstart, 
        tend, 
        tstatus
        from tboard t inner join member m
        on t.tmid=m.mid
        order by tnum desc;

CREATE OR REPLACE VIEW HLIST AS
SELECT H.HISNUM,    -- BNUM:빈 필드명과 컬럼명이 일치하면 편하다. 
       H.HISNAME,
       H.HISLOCA,
       H.HISEXPLAN,
       H.HISINFO
FROM hisboard H INNER JOIN MEMBER M
ON H.HISNAME=M.MNAME;

CREATE OR REPLACE VIEW tRLIST AS
SELECT trbNUM, tRNUM, tRCONTENTS, tRDATE, tRmID
FROM tREPLY
ORDER BY tRDATE DESC;

-- 테이블 변경 후, 필요하다면 샘플 데이터도 수정
INSERT INTO hisboard
VALUES(NULL,'장정리 오층석탑','인천광역시 강화군 하점면 장정리 산193','강화 장정리 오층석탑(江華 長井里 五層石塔)은 인천광역시 강화군 하점면 장정리에 있는 고려시대의 오층석탑이다. 대한민국의 보물 제10호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard 
VALUES(NULL,'전동사 대웅전','인천광역시 강화군 길상면 전등사로 37-41','강화 전등사 대웅전(江華 傳燈寺 大雄殿)은 인천광역시 강화군, 전등사 사찰에 있는 조선시대의 건축물이다. 1963년 1월 21일 대한민국의 보물 제178호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'정수사 법당','인천광역시 강화군 화도면 사기리 산86','강화 정수사 법당(江華 淨水寺 法堂)은 인천광역시 강화군 화도면 정수사에 있는 조선 시대의 건축물이다. 1963년 1월 21일 대한민국의 보물 제161호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'장정리 석조여래입상','인천광역시 강화군 하점면 장정리 584','강화 장정리 석조여래입상(江華 長井里 石造如來立像)은 인천광역시 강화군 하점면 장정리에 있는 고려시대의 석조 여래 입상이다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'전등사 약사전','인천광역시 강화군 길상면 전등사로 37-41','강화 전등사 약사전(江華 傳燈寺 藥師殿)은 인천광역시 강화군 전등사에 있는, 조선시대의 건축물이다. 1963년 1월 21일 대한민국의 보물 제179호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'경서동 녹청자 요지','인천 서구 검암동 산438-21','인천 경서동 녹청자 요지(仁川 景西洞 綠靑瓷 窯址)는 인천광역시 서구 경서동 일대의 언덕에 있는 고려시대 녹청자 가마터이다. 1970년 6월 8일 대한민국의 사적 제211호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'강화산성','인천 강화군 강화읍 국화리 산3번지 일원','강화산성(江華山城)은 인천광역시 강화군 강화읍 국화리에 있는 고려시대의 성곽이다. 1964년 6월 10일 대한민국의 사적 제132호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'고려궁지','인천 강화군 강화읍 북문길 42','강화 고려궁지(江華 高麗宮址)는 고려시대의 궁궐과 조선시대의 외규장각이 있었던 장소다. 위치는 인천광역시 강화군 강화읍 관청리에 있다. 1964년 6월 18일 대한민국의 사적 제133호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'강화 참성단','인천 강화군 화도면 흥왕리 산42-1','강화 참성단(江華 塹星壇)은 인천광역시 강화군 화도면 흥왕리, 마니산(摩尼山) 꼭대기에 있는 제단이다. 상고 시대 단군(檀君)이 쌓았다고 알려져 있으며, 1964년 7월 11일 대한민국의 사적 제136호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'부근리 지석묘','인천 강화군 하점면 부근리 317','강화 부근리 지석묘(江華 富近里 支石墓)은 인천광역시 강화군 하점면 부근에 있는 청동시대의 지석묘이다. 1964년 7월 11일 대한민국의 사적 제137호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'강화 홍릉','인천 강화군 강화읍 국화리 산180','강화 홍릉(江華 洪陵)은 고려의 제23대 왕인 고종의 능이다. 인천광역시 강화군 강화읍 국화리에 있다. 1971년 12월 28일 대한민국의 사적 제224호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'강화 초지진','인천 강화군 길상면 초지리 624','강화 초지진(江華 草芝鎭)은 인천광역시 강화군 길상면 초지리에 있는, 바다로 침입하는 외적을 막기 위하여 조선 효종 7년(1656년)에 구축하고 1679년 조선 숙종때 성으로 지은 요새이다. 1971년 12월 28일 대한민국의 사적 제225호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'덕진진','인천 강화군 불은면 덕성리 846	','강화 덕진진(江華 德津鎭)은 인천광역시 강화군 불은면에 있는 조선시대 강화 12진보(鎭堡)의 하나이다. 1971년 12월 28일 대한민국의 사적 제226호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'강화 광성보','인천 강화군 불은면 덕성리 833','강화 광성보(江華 廣城堡)는 인천광역시 강화군 불은면 덕성리에 있는 조선시대의 성곽시설이다. 1971년 12월 28일 대한민국의 사적 제227호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'선원사지','인천 강화군 선원면 지산리 산133','강화 선원사지(江華 仙源寺址)는 인천광역시 강화군 선원면 지산리에 있는 고려시대의 절터이다. 1977년 11월 29일 대한민국의 사적 제259호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'답동성당','인천 중구 우현로50번길 2','답동성당(沓洞聖堂)은 인천광역시 중구 답동에 위치한 천주교 인천교구의 주교좌 대성당으로, 주보성인은 성 바오로이다. 인천교구 현 교구장은 3대 정신철(요한 세례자) 주교(2016년 11월 10일 착좌)이고, 답동성당은 한 명의 주임 사제와 한 명의 보좌신부와 두 명의 수녀를 두고 있다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'갑곶돈','인천 강화군 강화읍 갑곳리 1020 외','강화 갑곶돈(江華 甲串墩)는 인천광역시 강화군 강화읍에 있는 조선시대의 돈대이다. 1984년 8월 13일 대한민국의 사적 제306호 갑곶돈으로 지정되었으나, 2011년 강화 갑곶돈으로 변경되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'강화 석릉','인천 강화군 양도면 길정리 산182','강화 석릉(江華 碩陵)은 인천광역시 강화군 양도면에 있는, 고려 희종(재위 1204∼1211)의 무덤이다. 1992년 3월 10일 대한민국의 사적 제370호 석릉으로 지정되었으나, 2011년 현재의 명칭으로 변경되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'강화 가릉','인천 강화군 양도면 능내리 산16-2','강화 가릉(江華 嘉陵)은 인천광역시 강화군 양도면에 위치한 고려 원종(재위 1259∼1274)의 왕비 순경태후의 능(陵)이다. 1992년 3월 10일 대한민국의 사적 제370호 가릉으로 지정되었으나, 2011년 현재의 명칭으로 변경되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'강화 곤릉','인천 강화군 양도면 길정리 산75','강화 곤릉(江華 坤陵)은 인천광역시 강화군 양도면에 있는 고려 강종의 부인 원덕태후 유씨의 무덤이다. 1992년 3월 10일 대한민국의 사적 제370호 곤릉으로 지정되었으나, 2011년 현재의 명칭으로 변경되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'대한성공회 강화성당','인천 강화군 강화읍 관청길 22	','강화성당(江華聖堂)은 인천광역시 강화군 강화읍 관청길에 있는, 대한제국시대에 세워진 성공회 서울교구 소속의 성당이다. 강화읍에 있기 때문에 강화읍성당이라고도 한다. 한국에서 최초로 지어진 한옥 성당이다. 2001년 1월 4일 대한민국의 사적 제424호로 지정되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'인천도호부관아','인천 남구 매소홀로 553','인천도호부관아(仁川都護府官衙)는 인천광역시 미추홀구 문학동에 있는 조선시대 지방 관청 건물이다. 1982년 3월 2일 인천광역시의 유형문화재 제1호 인천도호부청사로 지정되었다가, 2019년 10월 7일 현재의 명칭으로 변경되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'부평도호부관아','인천 계양구 계산동 943','부평도호부관아(富平都護府官衙)는 인천광역시 계양구, 인천부평초등학교에 있는 관아건물이다. 1982년 3월 2일 인천광역시의 유형문화재 제2호 부평도호부청사로 지정되었다가, 2019년 10월 7일 현재의 명칭으로 변경되었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'구 인천 일본 제1은행 지점','인천 중구 중앙동1가 9','일본의 제일은행의 지점으로 쓰기 위해 고종 광무 3년(1899)에 지은 석조건물이다. 일본인 니이노미 다카마사가 설계한 건물로 모래, 자갈, 석회를 제외한 나머지의 모든 건축 재료를 일본에서 직접 가져와 만들었다.','눈으로만 보세요.');
INSERT INTO hisboard
VALUES(NULL,'부평향교','인천 계양구 계산동 982-1','부평향교(富平鄕校)는 인천광역시 계양구 계산동에 있는 조선시대 향교이다. 1990년 11월 9일 인천광역시의 유형문화재 제12호이다','눈으로만 보세요.');

COMMIT;
