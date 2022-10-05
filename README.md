
<h3>사용 언어<br/><br/>
  <img src="https://img.shields.io/badge/Kotlin-7e4dd2?style=flat-square"/>
</h3>
    
<h3><b>아키텍쳐 구조 및 패턴<br/><br/>
<img src="https://img.shields.io/badge/Clean Architecture-7732ff?style=flat-square"/>
<img src="https://img.shields.io/badge/Observer Pattern-7732ff?style=flat-square"/>
  </b></h3>

<h3><b>사용 라이브러리<br/><br/>
<img src="https://img.shields.io/badge/Android AAC-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/DataBinding-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/Navigation-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/Paging3-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/Hilt-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/Coroutine-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/Flow-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/Retrofit-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/OkHttp3-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/Glide-66459B?style=flat-square"/>
<img src="https://img.shields.io/badge/Room-66459B?style=flat-square"/>
  </b></h3><br/>

## 프로젝트 목적

<ul>
  <li>Android의 다양한 디자인 패턴과 Clean Architecture 개념 습득 및 이해.</li>
  <li>Google에서 발표되는 최신 동향 기술들 및 다양한 필요 라이브러리 이해 및 적용, 습득.</li>
  <li>클린 코드, 리팩터링 서적의 내용을 베이스로 개발 습관 개선.</li>
  <li>FrontSize, BackendSide, Design, 기획등 혼자 전체적으로 경험함으로써 각 Side별 이해관계 습득.</li>
</ul>
</br>

## 프로젝트 설명

<ul>
  <li>앱 이름 : MyTravel</li>
  <li>앱 컨셉 : 국내 여행지 공유 및 기록, 지역별 여행지별 다양한 정보 주고받기 </li>
  <li>앱 소개 : 
    <br/> - 내가 다녀온 여행지 기록.
    <br/> - 하나의 장소에 여러 사람들이 찍은 사진을 모아봄으로써 그 여행지의 다양한 모습 보기 가능.
    <br/> - 지역 혹은 지명만 정하고, 어딜가야 할지 모를때를 위한 지역별 장소 모아보기 기능.
    <br/> - (추후) 여행 카테고리별 장소 및 여행지 추천, 장소에 대한 경험 댓글 공유, 여행 계획 짜기 기능 및 관리
  </li>
  
  <br/><li>앱 시연 연상 :
      <br/>현재 Api Server가 Local이다보니 시연영상을 구글 드라이브에 올려놨습니다.
      <br/>양해 부탁드립니다 :)</br></br>
  </li>
</ul>

   [영상 URL](https://drive.google.com/file/d/1bSdGM8OuAWQj5FaGTJSNvyf35CoF44Ex/view?usp=sharing)
   
</br>

## 간단한 앱 구조 설명

### 패키지 구조

<b>전체 패키지 구조</b>는 Clean Architecture 개념에 따라 3개의 Layer로 나누고, MultiModule로 분리.<br/>
  <li>Presentation(=app)</li>
  <li>Domain</li>
  <li>Data</li>
  
</br>![스크린샷 2022-10-05 오후 2 57 39](https://user-images.githubusercontent.com/12716889/193991256-3594b8da-aff1-4d53-a603-228542199f36.png) </br>

<hr width="600px" size="2px">

<b>Presentation Layer</b>의 Package구조는 </br>
Feature 별로 Package를 구성하고 있으며,</br>
각 Feature별 AAC ViewModel을 사용.</br>

![스크린샷 2022-10-05 오후 3 25 25](https://user-images.githubusercontent.com/12716889/193994852-ebe1685e-e3f2-475c-89f1-476735e160f4.png) </br>


Single Activity Architecture 구조를 사용하고 있고, </br>
Multiple Fragments와 JetPack Navigation을 이용해 앱 흐름을 만듬. </br>


