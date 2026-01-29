# Android Studio 프로젝트 설정 가이드

## ✅ 체크리스트

설정을 완료하기 전에 다음 항목들을 확인하세요:

- [ ] Android Studio 설치됨 (Arctic Fox 이상)
- [ ] JDK 17 설치됨
- [ ] Firebase 프로젝트 생성됨
- [ ] google-services.json 다운로드됨
- [ ] PC IP 주소 확인함
- [ ] Vue.js 개발 서버 실행 가능함

## 📖 단계별 설정

### 1단계: Android Studio에서 프로젝트 열기

```
1. Android Studio 실행
2. File → Open
3. VueTabletApp 폴더 선택
4. "Trust Project" 클릭
5. Gradle Sync 자동 실행 대기
```

**초기 로딩 시간:** 3-5분 (인터넷 속도에 따라)

### 2단계: Firebase 설정

#### 2-1. Firebase 프로젝트 생성

1. https://console.firebase.google.com 접속
2. "프로젝트 추가" 클릭
3. 프로젝트 이름 입력 (예: vue-tablet-app)
4. Google 애널리틱스 활성화 (선택사항)
5. "프로젝트 만들기" 클릭

#### 2-2. Android 앱 등록

1. Firebase 콘솔에서 생성한 프로젝트 선택
2. "Android 앱에 Firebase 추가" 클릭 (또는 설정 톱니바퀴 → 프로젝트 설정)
3. 패키지 이름 입력: **com.yourcompany.vueapp**
4. 앱 닉네임: VueTabletApp (선택사항)
5. "앱 등록" 클릭

#### 2-3. google-services.json 다운로드

1. "google-services.json 다운로드" 버튼 클릭
2. 다운로드한 파일을 프로젝트의 **app/** 폴더에 복사

   ```
   VueTabletApp/
   └── app/
       ├── google-services.json  ← 여기에 복사!
       ├── build.gradle
       └── src/
   ```

3. Android Studio에서 "Sync Now" 클릭

#### 2-4. Cloud Messaging 활성화

1. Firebase 콘솔 → Cloud Messaging
2. 서버 키 확인 (나중에 백엔드에서 사용)

### 3단계: 개발 서버 URL 설정

#### 3-1. PC IP 주소 확인

**Windows:**
```cmd
ipconfig
```
→ IPv4 주소 확인 (예: 192.168.0.10)

**Mac:**
```bash
ifconfig | grep "inet "
```

**Linux:**
```bash
ip addr show
```

#### 3-2. MainActivity.kt 수정

1. Android Studio에서 파일 열기:
   ```
   app/src/main/java/com/yourcompany/vueapp/MainActivity.kt
   ```

2. 24번째 줄 수정:
   ```kotlin
   // 에뮬레이터 사용 시:
   private val DEV_URL = "http://10.0.2.2:5173"
   
   // 실제 기기 사용 시 (본인 PC IP로 변경):
   private val DEV_URL = "http://192.168.0.10:5173"  // ← 수정!
   ```

### 4단계: Vue.js 개발 서버 실행

1. Vue 프로젝트 폴더로 이동:
   ```bash
   cd your-vue-project
   ```

2. 네트워크 접근 가능하게 서버 실행:
   ```bash
   npm run dev -- --host
   ```

3. 출력 확인:
   ```
   ➜  Local:   http://localhost:5173/
   ➜  Network: http://192.168.0.10:5173/  ← 이 주소 사용!
   ```

4. Network 주소를 MainActivity.kt에 입력

### 5단계: 앱 빌드 및 실행

#### 5-1. 에뮬레이터 사용 (PC에서 테스트)

1. **Device Manager 열기:**
   ```
   Tools → Device Manager
   ```

2. **에뮬레이터 생성** (없는 경우):
   ```
   Create Device 클릭
   → Tablet 카테고리 선택
   → Pixel Tablet 선택
   → System Image: API 33 (Android 13) 다운로드
   → Finish
   ```

3. **앱 실행:**
   ```
   상단 툴바: 에뮬레이터 선택
   → ▶ Run 버튼 (또는 Shift + F10)
   ```

4. **DEV_URL 설정:**
   ```kotlin
   private val DEV_URL = "http://10.0.2.2:5173"  // 에뮬레이터용
   ```

#### 5-2. 실제 기기 사용 (태블릿)

1. **태블릿 설정:**
   ```
   설정 → 휴대전화 정보 → 빌드 번호 7번 터치
   → 개발자 모드 활성화
   설정 → 개발자 옵션 → USB 디버깅 켜기
   ```

2. **USB 연결:**
   ```
   태블릿을 PC에 USB로 연결
   → "이 컴퓨터를 항상 허용" 체크
   → 확인
   ```

3. **앱 실행:**
   ```
   상단 툴바: 연결된 태블릿 선택
   → ▶ Run 버튼
   ```

4. **DEV_URL 설정:**
   ```kotlin
   private val DEV_URL = "http://192.168.0.10:5173"  // PC IP
   ```

5. **Wi-Fi 확인:**
   - PC와 태블릿이 같은 Wi-Fi 네트워크에 연결되어 있어야 함

### 6단계: 동작 확인

1. **앱이 실행되면:**
   - Vue.js 화면이 보여야 함
   - 흰 화면이 보이면 Logcat 확인

2. **Logcat 확인:**
   ```
   Android Studio → Logcat
   → 필터: "WebView Console"
   ```

3. **JavaScript 통신 테스트:**
   
   Vue 컴포넌트에서:
   ```javascript
   mounted() {
     if (window.Android) {
       window.Android.showToast("연결 성공!");
     }
   }
   ```

## 🔧 고급 설정

### 패키지 이름 변경 (선택사항)

기본 패키지 이름 `com.yourcompany.vueapp`을 변경하려면:

1. **AndroidManifest.xml:**
   ```xml
   <manifest package="com.mynewcompany.myapp">
   ```

2. **app/build.gradle:**
   ```gradle
   namespace 'com.mynewcompany.myapp'
   applicationId "com.mynewcompany.myapp"
   ```

3. **폴더 구조 변경:**
   ```
   java/com/yourcompany/vueapp/
   → java/com/mynewcompany/myapp/
   ```

4. **모든 Kotlin 파일:**
   ```kotlin
   package com.mynewcompany.myapp
   ```

5. **Firebase 재설정:**
   - 새 패키지 이름으로 Android 앱 재등록
   - google-services.json 다시 다운로드

### 앱 이름 변경

`app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">내 앱 이름</string>
```

### 화면 방향 설정

AndroidManifest.xml의 MainActivity:
```xml
<!-- 가로 모드 (현재 설정) -->
android:screenOrientation="landscape"

<!-- 세로 모드 -->
android:screenOrientation="portrait"

<!-- 자동 회전 -->
android:screenOrientation="sensor"
```

## ⚠️ 문제 해결

### "google-services.json 파일을 찾을 수 없음"

**해결:**
1. google-services.json이 `app/` 폴더에 있는지 확인
2. 파일 이름이 정확히 `google-services.json`인지 확인
3. Gradle Sync 재실행

### "localhost 연결 안 됨"

**체크리스트:**
- [ ] PC와 기기가 같은 Wi-Fi에 연결됨
- [ ] Vue 서버가 `--host` 옵션으로 실행 중
- [ ] MainActivity.kt의 IP가 올바름
- [ ] 방화벽에서 포트 5173 허용됨

**Windows 방화벽 허용:**
```
Windows Defender 방화벽 → 고급 설정
→ 인바운드 규칙 → 새 규칙
→ 포트 → TCP → 5173
```

### "Gradle Sync 실패"

**해결:**
```
1. File → Invalidate Caches → Invalidate and Restart
2. Build → Clean Project
3. Build → Rebuild Project
```

### "빌드 오류"

**해결:**
```
1. JDK 버전 확인:
   File → Project Structure → SDK Location
   → JDK: 17 이상

2. Gradle 버전 확인:
   gradle/wrapper/gradle-wrapper.properties
   → distributionUrl: gradle-8.2-bin.zip

3. 의존성 다운로드:
   Tools → SDK Manager
   → SDK Tools → Google Play services 체크
```

## 📱 다음 단계

설정이 완료되었으면:

1. **기능 추가:**
   - WebAppInterface.kt에 커스텀 메서드 추가
   - Vue에서 새로운 Android 기능 사용

2. **디버깅:**
   - Chrome DevTools (`chrome://inspect`)
   - Logcat 필터 사용

3. **배포 준비:**
   - 앱 아이콘 변경
   - ProGuard 설정
   - Release 빌드

## 💡 개발 팁

### 빠른 개발 사이클

```bash
# Terminal 1: Vue 서버 (항상 실행 상태 유지)
npm run dev -- --host

# Android Studio: 코드 수정 후 Hot Reload
# Vue 코드는 자동 새로고침
# Kotlin 코드는 재빌드 필요
```

### 효율적인 디버깅

```
1. Logcat 필터 저장:
   - "WebView Console" 필터 생성
   - "FCM" 필터 생성
   
2. Chrome DevTools 북마크:
   chrome://inspect
   
3. ADB 명령어:
   adb devices  # 연결된 기기 확인
   adb logcat   # 로그 실시간 확인
```

## 🎉 완료!

모든 설정이 완료되었습니다!

이제 Vue.js로 화면을 만들고, Android 네이티브 기능을 자유롭게 사용할 수 있습니다.

**다음 작업:**
- README.md 읽기 (API 사용법)
- Push 알림 테스트
- 커스텀 기능 추가
