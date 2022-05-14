package com.finedustApp.finedustalarm;

public class LocationTxt {
    static String[] finedustsidoData = {
            "서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종"
    };

    static String[] finedustStationData = {
            // 서울
            "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구",
            "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구", "도산대로", "강남대로", "천호대로", "시흥대로", "화랑로", "한강대로", "청계천로", "종로", "강변북로",
            "홍릉로", "정릉로", "신촌로", "공항대로", "영등포로",

            // 부산
            "개금동", "광복동", "광안동", "기장읍", "녹산동", "당리동", "대신동", "대연동", "대저동", "덕천동", "덕포동", "명장동", "명지동", "부곡동", "수정동", "연산동", "용수리", "용호동",
            "장림동", "재송동", "전포동", "좌동", "청룡동", "청학동", "태종대", "학장동", "화명동", "화동동", "부산항", "초량동", "온천동", "삼락동", "부산북항",

            // 대구
            "남산1동", "내당동", "다사읍", "대명동", "만촌동", "본동", "산격동", "서호동", "수창동", "시지동", "신암동", "유가읍", "이현동", "지산동", "진천동", "침산동", "태전동", "호림동",
            "화원읍", " 평리동", "이곡동",

            // 인천
            "검단", "계산", "고잔", "구월동", "길상", "논현", "동춘", "부평", "삼산", "서창", "석남", "송도", "송림", "송해", "숭의", "신흥", "아암", "연희", "영종", "영흥", "운서", "원당",
            "주안", "청라", "서해", "석바위", "부평역", "남동", "중봉", "송현", "경인항", "인천항", "석모리", "덕적도", "백령도", "연평도", "울도",

            // 광주
            "건국동", "노대동", "농성동", "두암동", "서석동", "오선동", "우산동(광주)", "유촌동", "일곡동", "주월동", "평동", "치평동", "운암동",

            // 대전
            "관평동", "구성동", "노은동", "대성동", "둔산동", "문창동", "문평동", "상대동(대전)", "성남동1", "읍내동", "정림동", "대흥동1", "월평동",

            // 울산
            "농소동", "대송동", "덕신리", "무거동", "범서읍", "부곡동(울산)", "삼남읍", "삼산동", "상남리", "성남동", "신정동", "야음동", "약사동", "여천동(울산)", "웅촌면", "전하동", "화산리",
            "효문동", "북부순환도로", "울산항", "신정로",

            // 경기
            "가남읍", "가평", "경안동", "고덕면", "고색동", "고읍", "고잔동", "고천동", "고촌읍", "곤지암", "공도읍", "과천동", "광교동", "교문동", "금곡동", "금촌동", "기흥", "김량장동", "남양읍",
            "내동", "단대동", "당동", "대부동", "대신면", "대야동", "동구동", "동탄", "모현읍", "목감동", "미사", "배곧동", "백석읍", "백암면", "별내동", "별양동", "보산동", "복정동", "본오동",
            "봉당읍", "봉산동", "부곡3동", "부곡동1", "부림동", "부발읍", "비전동", "사우동", "산본동", "상대원동", "새솔동", "서신면", "선단동", "설악동", "소사본동", "소하동", "송북동", "송산3동",
            "수내동", "수지", "시화산단", "식사동", "신원동", "신장동", "신풍동", "안양2동", "안양8동", "안중", "양평읍", "연천", "영통동", "오남읍", "오산동", "오정동", "오포읍", "와부읍", "용문면",
            "우정읍", "운정", "운중동", "원곡동", "원시동", "월곶면", "의정부1동", "의정부동", "이동읍", "인계동", "일동면", "장현동", "장호원읍", "정왕동", "정자동", "주엽동", "죽산면", "중2동",
            "중앙동(경기)", "진접읍", "창전동", "천천동", "철산동", "청계동", "청북읍", "파주읍", "평택항", "한강신도시", "행신동", "향남읍", "호계동", "호매실", "호수동", "화도읍", "중앙대로(고잔동)",
            "서해안로", "경춘로", "파주", "백마로(마두역)", "중부대로(구갈동)", "설성면", "관인면", "한강로", "금암로(신장동)", "연천(DMZ)", "경수대로(동수원)", "대왕판교로(백현동)", "성남대로(모란역)",
            "송내대로(중동)",

            // 강원
            "갈말읍", "금호동", "남양동1", "문막읍", "반곡동(명륜동)", "상리", "석사동", "신사우동", "양구읍", "양양읍", "영월읍", "옥천동", "인제읍", "정선읍", "주문진읍", "중앙동(강원)", "중앙로",
            "지정면", "천곡동", "평창읍", "홍천읍", "화천읍", "황지동", "횡성읍", "온의동", "동해항", "묵호항", "철원(DMZ)", "화천(DMZ)", "인제(DMZ)", "고성(DMZ)", "북평면", "간성읍", "치악산",

            // 충북
            "가덕면", "감물면", "괴산읍", "단성면", "단양읍", "덕산읍", "도안면", "매포읍", "보은읍", "사천동", "산남동", "살미면", "소이면", "송정동(봉명동)", "영동읍", "영천동", "오송읍", "오창읍",
            "옥천읍", "용담동", "용암동", "음성읍", "장락동", "중앙탑면", "증평읍", "진천읍", "청풍면", "칠금동", "호암동", "황간면", "복대동", "청천면", "금왕",

            // 충남
            "공주", "금산읍", "내포", "논산", "당진시청사", "대산리", "대천2동", "도고면", "독곶리", "동문동", "둔포면", "모종동", "배방읍", "백석동", "부여읍", "삽교읍", "서면", "서천읍", "성거읍",
            "성동면", "성연면", "성황동", "송산면", "송악면", "신방동", "엄사면", "연무읍", "예산군", "원북면", "이원면", "인주면", "장항읍", "정산면",  "주교면", "청양읍", "탄천면", "태안읍", "홍성읍",
            "성성동", "사곡면", "장재리", "파도리", "격렬비열도", "외연도", "평택당진항", "대산항", "장항항",

            // 전북
            "계화면", "고산면", "고창읍", "관촌면", "구이면", "노송동", "모현동", "무주읍", "봉동읍", "부안읍", "비응도동", "사정동", "삼기면", "삼천동", "소룡동", "소룡동2", "송천동", "순창읍", "서신동",
            "신태인", "신풍동(군산)", "심원면", "여산면", "연지동", "영파동", "옥산면", "요촌동", "용동면", "운봉읍", "임실읍", "장수읍", "죽항동", "진안읍", "춘포면", "팔복동", "팔봉동", "함열읍", "혁신동",
            "말도", "함열읍", "금마면", "새만금", "운암면", "관촌면", "무주읍", "군산항",

            // 전남
            "강진읍", "고흥읍", "곡성읍", "광양읍", "구례읍", "담양읍", "대불", "덕충동", "무안읍", "문수동", "벌교읍", "보성읍", "봉강면", "부흥동", "빛가람동", "삼일동", "서강동", "순천만", "신대", "신안군",
            "신지면", "여천동(여수)", "연향동", "영광읍", "영암읍", "용담동", "월내동", "율촌면", "장성읍", "장천동", "장흥읍", "중동(유해+중금속)", "진도읍", "태인동", "함평읍", "해남읍", "호두리", "화순읍",
            "화양면", "송단리", "안마도", "홍도", "가거도", "목포항", "광양항", "여수항",

            // 경북
            "3공단", "4공단", "가흥동", "공단동", "군위읍", "대가야읍", "대광동", "대도동", "대송면", "명륜동", "문경시", "보덕동", "봉화군청", "상주시", "석포면", "성건동", "성주군", "송도동", "안강읍", "안계면",
            "연일읍", "영덕읍", "영양군", "영주동", "영천시", "영해면", "예천군", "오천읍", "외동읍", "울릉읍", "원평동", "율곡동", "의성읍", "장량동", "장흥동", "제철동", "중방동", "진미동", "청림동", "청송읍",
            "칠곡군", "평화남산동", "하양읍", "형곡동", "화양읍", "강구면", "화북면", "안계면(교외)", "태하리", "포항항", "우현동",

            // 경남
            "가야읍", "거창읍", "경화동", "고성읍", "고현동", "금성면", "남해읍", "내서읍", "내일동", "대안동", "동상동", "명서동", "무전동", "물금읍", "봉암동", "북부동", "사천읍", "사파동", "산청읍", "삼방동",
            "삼호동", "상대동(진주)", "상봉동", "성주동", "아주동", "용지동", "웅남동", "월영동", "의령읍", "장유동", "정촌면", "진영읍", "창녕동", "하동읍", "함양읍", "합천읍", "향촌동", "회원동", "김해대로", "저구리",
            "대산면", "남상면", "마산항", "부산항", "반송로",

            // 제주
            "강정동", "남원읍", "대정읍", "동홍동", "성산읍", "연동", "이도동", "조천읍", "한림읍", "화북동", "노형로", "고산리",

            // 세종
            "부강면", "신흥동", "아름동", "한솔동", "보람동"
    };
}
