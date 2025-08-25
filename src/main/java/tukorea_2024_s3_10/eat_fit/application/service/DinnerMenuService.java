package tukorea_2024_s3_10.eat_fit.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.application.dto.DinnerMenuResponse;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.FoodItem;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserIntakeGoalRepository;
import tukorea_2024_s3_10.eat_fit.security.util.SecurityUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DinnerMenuService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;
    private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private final DietRecordRepository dietRecordRepository;
    private final UserIntakeGoalRepository userIntakeGoalRepository;

    // 음식명과 이미지 URL 매핑
    private static final Map<String, String> foodImageMap = Map.ofEntries(
            Map.entry("간장게장", "https://i.namu.wiki/i/vUw12c7iwLUBQqgZ3cpp05yFeakqOeSSmAaoFWInvkftwEnT2plZFbrMbQsrIPtog7Mbad0huqTzdsHV_nBB1g.webp"),
            Map.entry("갈비찜", "https://i.namu.wiki/i/9xHkxjyIHj2yj9fFf7eeyK8YJE3Lu3gJpFEDpe8cNwuMl2hOm61RE7S6607J1KwdvvcejL2J2b2kdS6y_UL0JQ.webp"),
            Map.entry("갈비탕", "https://i.namu.wiki/i/1SZq01CgLzMK0fg0D4VbbdduF9wHRsUxeB6V0XcEMCl1h8gFYc-f2cENXW6J4GfvrnQ9KlirV11qwbFgtZttig.webp"),
            Map.entry("갈치조림","https://semie.cooking/image/contents/recipe/rw/je/xitpmpzr/142882115gpuf.jpg"),
            Map.entry("감자채볶음","https://recipe1.ezmember.co.kr/cache/recipe/2021/11/07/5f1b5b140354f980ded6ba9d4e45b1091.jpg"),
            Map.entry("감자탕","https://recipe1.ezmember.co.kr/cache/recipe/2022/06/02/f5bb1e5a193f0fad313b901cf7a8fec71.jpg"),
            Map.entry("갓김치","https://i.namu.wiki/i/4EW7VzUkEobajV5QsUMoBV-nwMxC6ZTqrvqnzm3_O1OnWYBkVJ44gScHW3EqVOeWPbWydMed-_OMiclTLEuRiw.webp"),
            Map.entry("계란국","https://i.namu.wiki/i/Z0p61mfiMtBIsT3y4rD2_ueGIg64PQisulOIZlua_I8XhaP53AiNVVkqRIAEC7haddW3RXpwKmTV5-pqpHLJCw.webp"),
            Map.entry("계란말이","https://recipe1.ezmember.co.kr/cache/recipe/2019/07/29/81147460a9faf7bdb78740b34758a5651.jpg"),
            Map.entry("계란찜","https://recipe1.ezmember.co.kr/cache/recipe/2018/10/09/8f79724e7d677383a950b5b1bf5e78ba1.jpg"),
            Map.entry("계란후라이","https://i.namu.wiki/i/e9Bl02oMHxHA9bx-wFr0tiJh6TuqC5afv0X2Qyt3G21HlP24y-mPiFP9BTMHhrYKAs-p7MziD87I2D9Q7VpI6Q.webp"),
            Map.entry("고등어구이","https://i.namu.wiki/i/7URwu8QHWS9V4sUvGaBOHW7gL1S1RDIfvE1dYeB2lZsEa53--LqZaJdA7I65us_VzVmP9SnvDKlQ2K037PHt8g.webp"),
            Map.entry("고사리나물", "https://recipe1.ezmember.co.kr/cache/recipe/2015/06/17/7c25fabb4f914839ab1b99b06783c5b3.jpg"),
            Map.entry("곱창구이", "https://spdy-flexg-87.flexgate.co.kr/data/goods/gksrud0929/small/thum2/00%EC%86%8C%EA%B3%B1%EC%B0%BD%EA%B5%AC%EC%9D%B41000x1000.jpg"),
            Map.entry("김밥", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6BN2ComudkJubd-AeSzVwQjdsym3CB7OSnw&s"),
            Map.entry("김치볶음밥", "https://i.namu.wiki/i/QmCg_wtSaWqRXR5-gIBfAy36a14b7WOzSwMIu3AC7BECbdRKN1uOb22mes9px6thnhl2kXh1eKUWStoQLLX9WQ.webp"),
            Map.entry("김치전", "https://recipe1.ezmember.co.kr/cache/recipe/2019/05/02/ddafc8912fdd1c261dd673cec48b96861.jpg"),
            Map.entry("김치찌개", "https://i.namu.wiki/i/8drgvI-cQLUfJDC00zbl2ZolK4W3o4ZkVSpR-zM5FZk_QzT58vYnx_7ohk0qwGYYiSLPiZgwccyIEFUtYKDjUQ.webp"),
            Map.entry("김치찜", "https://static.wtable.co.kr/image/production/service/recipe/1772/509a4bbe-1fa9-448a-a487-240db9b0aba0.jpg"),
            Map.entry("까르보나라", "https://recipe1.ezmember.co.kr/cache/recipe/2020/08/09/b53479e644967fdb26161f95fa0d74531.jpg"),
            Map.entry("깍두기", "https://i.namu.wiki/i/m60Bqkh0U5nkCdqLMe45oHuZ9jQkOQScYpm3RX4Y7hiH7ietwSJBOv-Jys9pATlpkIQ_TvXpvXWHjGDnTTTpww.webp"),
            Map.entry("깻잎장아찌", "https://recipe1.ezmember.co.kr/cache/recipe/2017/07/13/a5aa69473771801460425a4e3c5f4fae1.jpg"),
            Map.entry("누룽지", "https://m.mommindmall.com/web/product/big/202208/2967c3908033f77fa75d1911d003150a.jpg"),
            Map.entry("닭갈비", "https://i.namu.wiki/i/GChQPUF2-YxCfpGGTBmNyXI44LpfjXTtUFcLlOSNT-FkdQBxZ0ZmrVdqX6DASq6LCDzXJutIg7hn3k1-hXU3dQ.webp"),
            Map.entry("닭볶음탕", "https://i.namu.wiki/i/poj3srIlqP-GipsjqmxWSwkTB9rC6SIeXC9EzE4t652HfKf4ottUP0RGOExdk9WRvDKFbTrp1lJW6ZXBjxUugQ.webp"),
            Map.entry("도토리묵", "https://i.namu.wiki/i/UZq2vs2i2gwLC4r1LVKRlFDSuXTVTWiTDf5gGgrsUxjSt1wPpOvMEl36rqQFC34dopgAglGlEso8MD5RuVW7hw.webp"),
            Map.entry("동그랑땡", "https://recipe1.ezmember.co.kr/cache/recipe/2023/04/03/826d0fdf227c683c50989f76a0bfda351.jpg"),
            Map.entry("된장찌개", "https://i.namu.wiki/i/CcKeSwi7PPpUqL9ZjYm3Iv0J4Nq3c-_Vu1655ja7vlOrDa8yVWAfN6PxW6KAdWYYW4almcmGLSjFBunR_27xpg.webp"),
            Map.entry("두부김치", "https://i.namu.wiki/i/XQWO5vnRw9XMQZkiKj528Xh3ug0XzYD4dhp3-7shU5EZ78lcAV0_a7Jh2ZhpAxx5gORqS_vxeAQqTuYc0-zHsg.webp"),
            Map.entry("떡갈비", "https://i.namu.wiki/i/qjHR-erhPWGreIR7Gy_iDL1JgBtbMTfIga0n8PJxXHXr15LnAyWRVllhPCrjj-941yQ8fb6pbsnDsG7goImmUQ.webp"),
            Map.entry("떡국", "https://i.namu.wiki/i/6a54kCxXaVDEG2VE4PUK_DILLGzV4zh3GYVDg1CtK3qz6hamJLSE7eI5b8vxLr0il6p8OvmHJ9kkJpSTX6OD1A.webp"),
            Map.entry("떡꼬치", "https://i.namu.wiki/i/uVICdqyI3FStRjZLqGWyTaCxN205bX0ImgJUn_HmfoDRTlgvzUYgVSx2UwBRKRBgOrgP9a7C8P-wd7EWGIl8Pg.webp"),
            Map.entry("떡볶이", "https://i.namu.wiki/i/A5AIHovo1xwuEjs7V8-aKpZCSWY2gN3mZEPR9fymaez_J7ufmI9B7YyDBu6kZy9TC9VWJatXVJZbDjcYLO2S8Q.webp"),
            Map.entry("라면", "https://i.namu.wiki/i/2mrBrfSDXbBJHLjgyycsAhB0qLvjSZbvswmGtVTAupaEfuQ8Xuzp8wJiXyeDTsqz7blqGRYSjW5mbo1Wc4GP_w.webp"),
            Map.entry("마카롱", "https://i.namu.wiki/i/Tj_fvrxM040xNHdlwJaXGoFshTDq_-bPNWWKxoZOULnFcYSMG8Fn_X5KXAR2RSShIpqinuA3yc6z29NwBcA95w.webp"),
            Map.entry("만두", "https://i.namu.wiki/i/BQKa8GCZMjCRCDBzbCF7uBtPO_aHoflLZOreUGY7WOlL16HwLqJpxZv9IoAI2lviaDIgGJWf0iR_QR-rA9gGAQ.webp"),
            Map.entry("매운탕", "https://recipe1.ezmember.co.kr/cache/recipe/2017/03/16/0e5a40c0cebe336b1e53f355391ecc431.jpg"),
            Map.entry("무국", "https://recipe1.ezmember.co.kr/cache/recipe/2017/05/15/3540ae57b0a8002442e8fe12898358d71.jpg"),
            Map.entry("물냉면", "https://static.wtable.co.kr/image/production/service/recipe/2272/cc6b7242-274f-490e-b935-0d6b86aff2c2.jpg?size=800x800"),
            Map.entry("미역국", "https://i.namu.wiki/i/gRYEv1mYsL0dRPEZheEF6R0nbu4QLoD3bb36HdRRP6Z-CP4yaU9x8GzJ7MK4NJMeogQPeiA495smmsAYwRha7g.webp"),
            Map.entry("미역줄기볶음", "https://recipe1.ezmember.co.kr/cache/recipe/2022/03/08/2653ae716fad5570a35d8b0e10ce9d641.jpg"),
            Map.entry("배추김치", "https://i.namu.wiki/i/3u7KQ8jVXWJMJpioMNBo5bFb7NrMd1jUbJrGXy99vSgh57D2w0BHG3RlEnawqyRlfAuTihp37sPnlCKX3IlKNg.webp"),
            Map.entry("백김치", "https://i.namu.wiki/i/O-J-qt2Qbqy4EdBvOBsx9Mkk9eJtZ38tZhKwr54igxSmHOtBTquxMDVLmRXrJqwKscdqV6nSJituwTwjZx77uQ.webp"),
            Map.entry("보쌈", "https://i.namu.wiki/i/jgm2bWyeBCIqOHEu3052S2TK-zyd_pHYeNmsUeUwgk9yuiPGt2C0JPEFgocGwVElJeMkwzPrHIuuNAVbSOzd_g.webp"),
            Map.entry("불고기", "https://i.namu.wiki/i/1fMv9BlDolXCcO2TlBW0zuV14FbmYAQf71zBGjY8RvtoP3x-zDBo0jiQxy4gdQ8ipfOqa9NNgGc5AOPVfRHlzQ.webp"),
            Map.entry("비빔냉면", "https://recipe1.ezmember.co.kr/cache/recipe/2017/08/19/0e94460820b12f63cd24176a39ab7be21.jpg"),
            Map.entry("비빔밥", "https://i.namu.wiki/i/dgjXU86ae29hDSCza-L0GZlFt3T9lRx1Ug9cKtqWSzMzs7Cd0CN2SzyLFEJcHVFviKcxAlIwxcllT9s2sck0RA.jpg"),
            Map.entry("삼겹살", "https://i.namu.wiki/i/oFHlYDjoEh8f-cc3lNK9jAemRkbXxNGwUg7XiW5LGS6DF1P2x8GCeNQxbQhVIwtUS1u53YPw-uoyqpmLtrGNJA.webp"),
            Map.entry("삼계탕", "https://static.wtable.co.kr/image-resize/production/service/recipe/1135/16x9/61697f80-97e2-445b-ae0d-bf4a65ae6e6c.jpg"),
            Map.entry("새우볶음밥", "https://recipe1.ezmember.co.kr/cache/recipe/2020/01/20/596cb8b52b25cb6b6d66eae42022d7401.jpg"),
            Map.entry("새우튀김", "https://i.namu.wiki/i/2TuQypWwLQRFbdnr66ze1ApUNoy-LM_7FQYlBAjA8-extffPMewvsla7csjiHK0H7Wlq8FlIQSyqXqgQyJZTlA.webp"),
            Map.entry("설렁탕", "https://upload.wikimedia.org/wikipedia/commons/e/e5/Seolleongtang2024.jpg"),
            Map.entry("소세지볶음", "https://recipe1.ezmember.co.kr/cache/recipe/2019/04/03/ee34a886695dd69c8df2996d27671be51.jpg"),
            Map.entry("송편", "https://www.kocis.go.kr/CONTENTS/BOARD/images/Seoul_songpyon_L1.jpg"),
            Map.entry("수제비", "https://recipe1.ezmember.co.kr/cache/recipe/2018/08/06/995888655ae22da585af1427d0969642.jpg"),
            Map.entry("숙주나물", "https://recipe1.ezmember.co.kr/cache/recipe/2023/03/22/f95daa72635be0e99b630d75d466988d1.jpg"),
            Map.entry("순대", "https://i.namu.wiki/i/XLAPWkGJqNSq_mm3VoMhPJbsw8brDna42Dluv-1Jed06w36mGyfgImfOwziYTc74G0JeIjpwOKvMWOYT-CA_IQ.webp"),
            Map.entry("순두부찌개", "https://static.wtable.co.kr/image-resize/production/service/recipe/1074/4x3/d3c0b5c1-2671-483e-9bbf-76496bb443fd.jpg"),
            Map.entry("스테이크", "https://i.namu.wiki/i/8HjXHmgc7e_5KZVmId552aTeMjiaDhyamd_XO9WUzD0AIG9jK4c6ULrJrec8MZMFiFm8tNO6wbr6sNGJkzbFJw.webp"),
            Map.entry("스파게티", "https://i.namu.wiki/i/_Y1KfAkYUNsL3o_nt_ok1BVfJJwEEO8xiVHMzsQO7fWvvtFwBAWWavROORH3dGOGtBry2kP77hhOnHNPk4Xz5Q.webp"),
            Map.entry("시금치나물", "https://recipe1.ezmember.co.kr/cache/recipe/2015/06/18/83d5b0a7dddcc3860440f04b6a046a54.jpg"),
            Map.entry("쌀국수", "https://i.namu.wiki/i/6OnClqASwpJipVMPuhtJG-QKt1iOgQWpc6CoVOGtmNWNUnuclcrJPJKeGVog00DqhPH26ovGUlrvd7tAmkHwQw.webp"),
            Map.entry("애호박볶음", "https://recipe1.ezmember.co.kr/cache/recipe/2017/05/11/a25ff5a93cf75b7cb8dea700dadf44181.jpg"),
            Map.entry("양념게장", "https://static.wtable.co.kr/image/production/service/recipe/1371/8910fd2b-942d-4890-8189-c56704d43c49.jpg"),
            Map.entry("양념치킨", "https://i.namu.wiki/i/6xYbbxwoWYs1HMi2lDKWC4Wdq7hCCRmp-m-_YfCBnCdPcJK7D67kT87qqiFtZws5lRw5aWH_B18VoCbI6qXd_Q.webp"),
            Map.entry("어묵볶음", "https://static.wtable.co.kr/image-resize/production/service/recipe/675/16x9/f7349d90-4576-4359-8f5e-8c28cf4a4764.jpg"),
            Map.entry("열무김치", "https://recipe1.ezmember.co.kr/cache/recipe/2016/10/06/7adfc6699ccc6be545789a69351299ef1.jpg"),
            Map.entry("오이소박이", "https://static.wtable.co.kr/image/production/service/recipe/230/7b0cc6e4-63c6-4caa-b97c-213fbb9a537c.jpg"),
            Map.entry("오징어튀김", "https://recipe1.ezmember.co.kr/cache/recipe/2015/04/09/2ae6a5accfc6665cd7816cc8b99433451.jpg"),
            Map.entry("와플", "https://i.namu.wiki/i/c-pI0gdR59-TiOqhOvttsFSYMKS1h-kgrH9_r51CPzUH5RgDWPHB3_WSZkg1c2d2NvillD55b4Mq7Br3npqihg.webp"),
            Map.entry("유부초밥", "https://recipe1.ezmember.co.kr/cache/recipe/2016/07/12/c336b9369d19142cccee8fdbf45e23be1.jpg"),
            Map.entry("육개장", "https://image.dongascience.com/Photo/2024/02/e1701ded5dc276737605b49a2acff20c.jpg"),
            Map.entry("육회", "https://t1.daumcdn.net/brunch/service/user/e00Q/image/Xb4Ex35ysi-cbnkvY3HtPwt2YWY.png"),
            Map.entry("잔치국수", "https://static.wtable.co.kr/image-resize/production/service/recipe/438/16x9/ed2bf141-5342-4804-ac34-6a30fb525b01.jpg"),
            Map.entry("잡채", "https://i.namu.wiki/i/XkSgHPX4Ek7uOdqN3VMhih3aHKVG4ga_EWmg42JVKSZ8GiPznUkXSu1Z85w-KbQFXvz1Yo8YOvTH31uyZ4itHQ.webp"),
            Map.entry("장조림", "https://recipe1.ezmember.co.kr/cache/recipe/2017/10/13/fa81b2850daffe53f0dfe65b2a80ae511.jpg"),
            Map.entry("제육볶음", "https://i.namu.wiki/i/npjMucg7sLxIm8Uca8O3lygeM9UX2Dsu4RVnVxcDdaItsLZ6w0N0Ju54gVqn8O7r7taBR6bAEwL9qOLoUKKbzg.webp"),
            Map.entry("족발", "https://i.namu.wiki/i/I63sEiy-8vUXVhV-I0IZiS9ntT0INuKXgBYAE3QqUvOlToSoEqSgpvEbUmxsFTXtoBRN4WJolyAFEAlDdeZFhQ.webp"),
            Map.entry("주꾸미볶음", "https://static.wtable.co.kr/image/production/service/recipe/707/d403b168-c865-4b5e-9afb-58c48e5feaec.jpg"),
            Map.entry("짜장면", "https://i.namu.wiki/i/j2AxLP9AtrcJebh4DVfGxowfXwI3a95dG_YZb_Ktczc6Ca7ACyd_NJL3YHQMw8SABGTQiJDwSpySOSSBLZVEZw.webp"),
            Map.entry("짬뽕", "https://i.namu.wiki/i/upNZ7cYsFsAfU0KcguO6OHMK68xC-Bj8EXxdCti61Jhjx10UCBgdK5bZCEx41-aAWcjWZ5JMKFUSaUGLC1tqWg.webp"),
            Map.entry("쫄면", "https://static.wtable.co.kr/image-resize/production/service/recipe/1033/1x1/be94ce10-5f15-4fbd-8923-91fe2bdb7b59.jpg"),
            Map.entry("찜닭", "https://static.wtable.co.kr/image/production/service/recipe/235/7c2b5692-bf30-474b-b056-48a2827cbded.jpg"),
            Map.entry("초밥", "https://i.namu.wiki/i/vwbKQLdn5CzWLTdLHKb2rYjLZS6icyFb6b6PdEz_3Y_0gJQqRMuhHGgogptO19gl3NZMnRsTvjYlA-0-JRSrbw.webp"),
            Map.entry("총각김치", "https://i.namu.wiki/i/ZvnSAVPc6mcGJUPanS-tGnu9CQiGKYWHcq7RkCH4FmHTVcZOntSTCxH_bIVeQMTIL8rR-_KIw8bxHmUmEpehQg.webp"),
            Map.entry("추어탕", "https://upload.wikimedia.org/wikipedia/commons/d/d8/Chueo-tang.jpg"),
            Map.entry("칼국수", "https://i.namu.wiki/i/XOXY4Q4ix1v8LxEHg0MejrPs8uT6SKWM57pexVwJ-pqK0c7y8so9b70RMIRqaJ0aI1lVD7Uy0auXNyP_bk8_Ig.webp"),
            Map.entry("콩국수", "https://i.namu.wiki/i/4ChBILJgL-UnL7OZnW3kbx84D0R9wfKnlLj159tKil6RrGtqYcF3M_-LLXqDnaNZVHGkGbXNn53t2SZVvpNULg.webp"),
            Map.entry("콩나물국", "https://recipe1.ezmember.co.kr/cache/recipe/2024/01/12/18665e408ca71f0e27c682cd0bdbd07a1.jpg"),
            Map.entry("콩나물무침", "https://recipe1.ezmember.co.kr/cache/recipe/2018/09/06/fd90bc74ddb4580b2dad22c83b42ec6f1.jpg"),
            Map.entry("콩자반", "https://i.namu.wiki/i/q9W9AogyEoc5FUFLVoRO1IlljpZbt71snXpxEdvRBWkKoq6j0Cf38nmUbc64YBmJFNWAjZVK3ZIY8QQNkXcA8Q.webp"),
            Map.entry("타코야끼", "https://i.namu.wiki/i/xFwG0IMgL8tbzL97wAh3-P9-itTeCy2eIc_nOw1QWNwHGtgbGgEMwM0V0IovQGA9I2kB-LmxfHi-ZPSAPwpcBA.webp"),
            Map.entry("파김치", "https://upload.wikimedia.org/wikipedia/commons/8/81/Scallion_kimchi.jpg"),
            Map.entry("파전", "https://i.namu.wiki/i/69pc47VRn-1HvaVi9WZ1e1jpGGyRummvNn0PA1X6HRYTh-bxb6UXSWe4OETi3TYGwXVrf_tAYWTgi2XBxrnlmQ.webp"),
            Map.entry("피자", "https://i.namu.wiki/i/umI-heVYVS9miQNqXM13FRUOHHL4l1nzsZgN9XRLFG7nI_7Dyf-Myr6HmiWf9Qd7SAZQz3WYSQHPXXtGAwLTag.webp"),
            Map.entry("해물찜", "https://i.namu.wiki/i/MlMaVlTiWauC_jVsdo7Q0mq6zxrd94VaEk6-lFGp9GYVA5-K27piJzKalPLG_Hfe0TOOn7o23TcifAZBVwktNg.webp"),
            Map.entry("햄버거", "https://m.health.chosun.com/site/data/img_dir/2024/10/16/2024101602160_0.jpg"),
            Map.entry("후라이드치킨", "https://i.namu.wiki/i/YVm0x8WHfLBtSyejD01_GTV1ITfWOJ-XODZzVTQPr386JsiBaz6Ucl1tKKxZmHiYStf_sXZBmK7AEXkEA18Tsg.webp"),
            Map.entry("훈제오리","https://recipe1.ezmember.co.kr/cache/recipe/2017/05/12/4f83e9770b9b2c0a21d76674ddf6553b1.jpg")
    );

    public DinnerMenuResponse getDinnerMenu() {
        return generateDinnerMenu();
    }

    public DinnerMenuResponse generateDinnerMenu() {
        try {
            // 프롬프트 구성 및 API 호출
            String prompt = buildPrompt();
            String aiResponse = callChatGPT(prompt);

            // 응답 예시: "탄수화물. 김치찌개, 불고기, 비빔밥"
            String[] parts = aiResponse.split("\\.");
            String nutrient = parts[0].trim();
            String[] foods = parts[1].trim().split(",\\s*");

            List<FoodItem> foodItems = new ArrayList<>();
            for (String food : foods) {
                String imageUrl = foodImageMap.getOrDefault(food, "https://yourcdn.com/images/default.jpg");
                foodItems.add(new FoodItem(food, imageUrl));
            }

            return new DinnerMenuResponse(nutrient, foodItems);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("OpenAI API 호출 실패", e);
        }
    }

    private String callChatGPT(String prompt) throws Exception {
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");
        ArrayNode messages = requestBody.putArray("messages");

        ObjectNode userMessage = objectMapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_ENDPOINT))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonNode = objectMapper.readTree(response.body());
        return jsonNode
                .path("choices").get(0)
                .path("message")
                .path("content")
                .asText();
    }


    private String buildPrompt() {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        LocalDate today = LocalDate.now();

        List<DietRecord> todayDietRecords = dietRecordRepository.findByUserIdAndDate(currentUserId, today);
        UserIntakeGoal userIntakeGoal = userIntakeGoalRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new RuntimeException("사용자의 영양 목표가 설정되지 않았습니다."));

        double carbRemain = userIntakeGoal.getCarbohydrateGoal();
        double proteinRemain = userIntakeGoal.getProteinGoal();
        double fatRemain = userIntakeGoal.getFatGoal();

        String foodList = String.join(", ",
                "간장게장", "갈비찜", "갈비탕", "갈치조림", "감자채볶음", "감자탕", "갓김치", "계란국",
                "계란말이", "계란찜", "계란후라이", "고등어구이", "고사리나물", "곱창구이", "김밥", "김치볶음밥",
                "김치전", "김치찌개", "김치찜", "까르보나라", "깍두기", "깻잎장아찌", "누룽지", "닭갈비",
                "닭볶음탕", "도토리묵", "동그랑땡", "된장찌개", "두부김치", "떡갈비", "떡국", "떡꼬치", "떡볶이",
                "라면", "마카롱", "만두", "매운탕", "무국", "물냉면", "미역국", "미역줄기볶음", "배추김치",
                "백김치", "보쌈", "불고기", "비빔냉면", "비빔밥", "삼겹살", "삼계탕", "새우볶음밥", "새우튀김",
                "설렁탕", "소세지볶음", "송편", "수제비", "숙주나물", "순대", "순두부찌개", "스테이크", "스파게티",
                "시금치나물", "쌀국수", "애호박볶음", "양념게장", "양념치킨", "어묵볶음", "열무김치", "오이소박이",
                "오징어튀김", "와플", "유부초밥", "육개장", "육회", "잔치국수", "잡채", "장조림", "제육볶음",
                "족발", "주꾸미볶음", "짜장면", "짬뽕", "쫄면", "찜닭", "초밥", "총각김치", "추어탕", "칼국수",
                "콩국수", "콩나물국", "콩나물무침", "콩자반", "타코야끼", "파김치", "파전", "피자", "해물찜",
                "햄버거", "후라이드치킨", "훈제오리"
        );


        for (DietRecord dietRecord : todayDietRecords) {
            carbRemain -= dietRecord.getCarbohydrate();
            proteinRemain -= dietRecord.getProtein();
            fatRemain -= dietRecord.getFat();
        }

        // 음수 방지
        carbRemain = Math.max(0, carbRemain);
        proteinRemain = Math.max(0, proteinRemain);
        fatRemain = Math.max(0, fatRemain);

        // 가장 부족한 영양소 찾기
        String deficientNutrient;
        if (carbRemain >= proteinRemain && carbRemain >= fatRemain) {
            deficientNutrient = "탄수화물";
        } else if (proteinRemain >= carbRemain && proteinRemain >= fatRemain) {
            deficientNutrient = "단백질";
        } else {
            deficientNutrient = "지방";
        }

        return String.format(
                "오늘 가장 부족한 영양소는 %s입니다. " +
                        "%s을 보충하기 좋은 음식 3가지를 아래 제공된 음식 리스트에서만 골라주세요. " +
                        "매번 다르게 추천하려고 노력하세요. " +
                        "가능하면 다양한 종류(국, 밥, 반찬 등)를 섞어서 고르세요. " +
                        "반드시 리스트에 없는 음식은 절대 고르지 마세요. " +
                        "출력은 반드시 다음 형식으로만 하세요: '%s. 음식1, 음식2, 음식3' " +
                        "예시 문구나 설명은 쓰지 마세요. " +
                        "음식 리스트: %s",
                deficientNutrient, deficientNutrient, deficientNutrient, foodList
        );
    }


}
