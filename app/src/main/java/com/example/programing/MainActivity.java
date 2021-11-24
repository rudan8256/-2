package com.example.programing;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        LatLng KNU = new LatLng(35.88939232163954, 128.61029970124557);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(KNU);
        markerOptions.title("경북대학교");
        markerOptions.snippet("대구캠퍼스");
        mMap.addMarker(markerOptions);

        markerOptions.snippet("서울시 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(37.49333598051108, 126.92717344042752))
                .title("SRC서울센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.500685848700755, 127.13547745577009))
                .title("서울곰두리체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.60531917849501, 126.90700759625147))
                .title("서부재활체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.64479099400291, 127.07296872693752))
                .title("동천재활체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.58423771642166, 126.96984709810044))
                .title("푸르메재활센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.55510301142267, 127.14861229625059))
                .title("서울장애인종합복지관 수중재활센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.56930533767856, 126.84900417111399))
                .title("기쁜우리체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.55106159272044, 127.09746902693557))
                .title("정립회관").alpha(0.7f));

        markerOptions.snippet("부산시 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(35.184731185051625, 129.06656288641068))
                .title("부산곰두리스포츠센터"));
        mMap.addMarker(markerOptions.position(new LatLng(35.180771116752275, 129.17649134223265))
                .title("한마음스포츠센터"));

        markerOptions.snippet("대구시 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(35.82753459725342, 128.6068409605555))
                .title("대구장애인종합복지관"));
        mMap.addMarker(markerOptions.position(new LatLng(35.85314870732212, 128.5253715285834))
                .title("대구시달구벌종합스포츠센타"));
        mMap.addMarker(markerOptions.position(new LatLng(35.82536966215067, 128.68472745556284))
                .title("대구시달구벌종합스포츠센타"));

        markerOptions.snippet("인천시 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(37.428910577119055, 126.70273526741238))
                .title("인천장애인국민체육센터"));

        markerOptions.snippet("광주시 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(35.18769991983212, 126.86847953071714))
                .title("광주광역시장애인종합복지관"));
        mMap.addMarker(markerOptions.position(new LatLng(35.13648568391075, 126.88014059805369))
                .title("광주광역시장애인체육회"));

        markerOptions.snippet("대전시 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(36.353611254000555, 127.30687634040524))
                .title("대전광역시장애인체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.30388363639965, 127.38265092506182))
                .title("산성종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.35513851020579, 127.38382065574781))
                .title("대전광역시서구건강체련관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.32538021870352, 127.32131257756386))
                .title("성세재활원").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.38253798370992, 127.32322670666521))
                .title("유성구장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.36809660604129, 127.41833022691237))
                .title("대전광역시립체육재활원").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.38775615585986, 127.42214549992666))
                .title("대덕구장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.268482983553035, 127.47174572691037))
                .title("동구아름다운복지관").alpha(0.7f));

        markerOptions.snippet("울산시 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(35.54776071084779, 129.33652451294876))
                .title("울산광역시장애인체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(35.564655329928875, 129.31562695758217))
                .title("울산광역시제2장애인체육관\n").alpha(0.7f));

        markerOptions.snippet("경기도 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(37.4791773343299, 126.84651491159148))
                .title("광명장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.695704020876555, 126.77304530974637))
                .title("홀트장애인종합체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(     37.703513430424024, 126.76501810974659  ))
                .title("고양시재활스포츠센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 37.322160832025226, 127.34977912623896      ))
                .title("성분도복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(    37.29591261936839, 127.03610591158794   ))
                .title("수원시장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(     37.29769001452801, 127.02491699809482  ))
                .title("보훈재활체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.40829654625318, 127.14426071343956       ))
                .title("성남시 한마음복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 37.38547582631473, 126.92687618275401      ))
                .title("안양시수리장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 37.34799126115022, 126.74164152693149    ))
                .title("시흥어울림국민체육센터").alpha(0.7f));

        markerOptions.snippet("강원도 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(37.360747304537036, 127.95376381343866))
                .title("원주드림체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.86265154436703, 127.75665476927037))
                .title("춘천국민체육센터\n").alpha(0.7f));

        markerOptions.snippet("충북 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(36.960170484298274, 127.91586526925272 ))
                .title("장애인형 국민체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.66922418025267, 127.4651973980825))
                .title("충북곰두리체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(37.13097579202391, 128.22991728459857 ))
                .title("제천시어울림체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 36.6788964517858, 127.47986922691835))
                .title("청주시장애인체육회").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.48852499772416, 127.72398758458603 ))
                .title("보은군노인장애인복지관").alpha(0.7f));

        markerOptions.snippet("충남 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(36.376943707469685, 126.58554429965304 ))
                .title("정심체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 36.813656210206986, 127.16915566924959))
                .title("천안장애인종합체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 36.6060669325754, 126.64582918273882))
                .title("홍성군장애인스포츠센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 36.7948092788988, 126.98329717293093))
                .title("아산시장애인복지관").alpha(0.7f));

        markerOptions.snippet("전북 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(35.81164389180442, 127.09193986923063 ))
                .title("전라북도장애인복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 35.57017496411877, 126.85640898456863))
                .title("정읍곰두리스포츠센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(35.80522750310232, 126.87178802505223 ))
                .title("김제시장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 36.01000995448678, 126.75821315574127))
                .title("전북장애인체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 35.85505060357082, 127.12583432690269))
                .title("전주 어울림국민체육센터").alpha(0.7f));

        markerOptions.snippet("전남 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng( 35.013439375374354, 126.69554210769549))
                .title("전남장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(34.76102660584698, 127.72350828455352 ))
                .title("여수시장애인국민체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 34.98209539478971, 127.57432147873583))
                .title("광양시장애인체육회").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(34.584571773453135, 126.3141515980434 ))
                .title("다목적체육관").alpha(0.7f));

        markerOptions.snippet("경북 장애인 체육설");
        mMap.addMarker(markerOptions.position(new LatLng( 36.44381677814163, 128.1548656176693))
                .title("상주시장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 35.88026076079109, 129.21807445573873))
                .title("경주시장애인체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.10620833803827, 128.33052111156496 ))
                .title("구미시장애인체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.65691876330655, 128.68370926924658 ))
                .title("애명체육관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(36.593580104336006, 128.18538296924535 ))
                .title("문경온누리스포츠센터").alpha(0.7f));

        markerOptions.snippet("경남 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng( 35.25162179394229, 128.6798740805021))
                .title("창원시장애인종합복지관").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng( 35.252953222435444, 128.63686915572694))
                .title("창원시립곰두리국민체육센터").alpha(0.7f));
        mMap.addMarker(markerOptions.position(new LatLng(34.99672150288304, 128.05992294222924 ))
                .title("사천시장애인국민체육센터").alpha(0.7f));

        markerOptions.snippet("제주 장애인 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng( 33.486815200669625, 126.43698379802375))
                .title("제주특별자치도장애인체육회").alpha(0.7f));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KNU, 11));
    }
}