# finedustApp
>한국환경공단이 제공하는 대기오염 및 측정소 정보 API를 사용한 내 위치 기반 미세먼지 정보 어플리케이션 (Android Only)

<pre>
<code>
주제 : 공공데이터를 사용한 어플리케이션 제작</br>
설명 : 한국환경공단이 제공하는 미세먼지 공공데이터를 사용해 스마트폰 GPS기반 미세먼지 예보 어플리케이션 구축</br>
팀 인원 : 2명</br>
맡은 역할 : 앱 개발</br>
개발 기간 : '22.03.08 ~ '22.04.07</br>
개발 언어 : Java</br>
개발 환경 : Windows 10 pro, Jdk 1.8, Android Studio Bumblebee
</code>
</pre>

[APK Download](https://github.com/hwisulee/finedustApp/raw/main/FinedustAlarm/app/release/app-release.apk)

## Table of Contents
1. [Preview](#preview)
2. [Library](#library)
3. [License](#license)


<h2 id="preview">Preview</h2>
1. 실행시 gps 기반 현재 내 위치로 정보 가시화

![preview1](https://user-images.githubusercontent.com/62528282/168412753-5c2fc44d-4636-4b5e-8de2-ddc5fca3cf05.gif)

2. MPAndroidChart를 사용한 데이터 가시화

![preview2](https://user-images.githubusercontent.com/62528282/168412755-eaddcca3-5884-4d67-a94b-1a619c0a5857.gif)

3. Kakao Maps API를 사용한 측정소 위치 및 내 위치 가시화

![preview3](https://user-images.githubusercontent.com/62528282/168412756-b90e3344-f30b-4a91-abd1-bfca5b33d215.gif)

<h2 id="library">Library</h2>

>This Project included this Library and API.

1. [한국환경공단_에어코리아_대기오염정보](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15073861)
2. [한국환경공단_에어코리아_측정소정보](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15073877)
3. [Kakao Maps API](https://apis.map.kakao.com)
4. [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

<h2 id="license">License</h2>

>Library and API has this Licenses

1. 한국환경공단_에어코리아_대기오염정보 & 2. 한국환경공단_에어코리아_측정소정보

공공저작물_출처표시 + 변경금지

![data-mark03-opencode3-m](https://user-images.githubusercontent.com/62528282/168411640-d45439e9-9ebd-4491-be10-3d13e3aa9592.png)

[More Information1](http://ccl.cckorea.org/about/)

[More Information2](https://www.kogl.or.kr/info/license.do#03-tab)

</br>

3. Kakao Maps API

OSS Notice | KakaoSDK2-Android
This application is Copyright © Kakao Corp. All rights reserved.

The following sets forth attribution notices for third party software that may be contained in this application.

[More Information](http://t1.daumcdn.net/osa/notice/173/1jnBpKCehN/notice.html)

</br>

4. MPAndroidChart

Copyright 2020 Philipp Jahoda

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

[More Information](https://github.com/PhilJay/MPAndroidChart/blob/master/LICENSE)

</br>

>My Project has this License

MIT License

Copyright (c) 2022 hwisulee

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
