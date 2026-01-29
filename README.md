# Vue Tablet App - Android WebView 프로젝트

태블릿용 Vue.js 하이브리드 앱 (localhost 개발 서버 연동)

## 🚀 빠른 시작

### 1. Android Studio에서 프로젝트 열기

```
Android Studio → File → Open
→ VueTabletApp 폴더 선택
→ Trust Project 클릭
```

### 2. Firebase 설정 (필수!)

1. **Firebase Console 접속**
   - https://console.firebase.google.com
   - "프로젝트 추가" 클릭

2. **Android 앱 등록**
   - 패키지 이름: `com.yourcompany.vueapp`
   - google-services.json 다운로드

3. **파일 복사**
   ```
   다운로드한 google-services.json → app/ 폴더에 복사
   ```

4. **Gradle Sync**
   - Android Studio 상단 "Sync Now" 클릭

### 3. 개발 서버 URL 설정

**MainActivity.kt** 파일 수정 (24번째 줄):

```kotlin
// PC IP 확인 후 수정
private val DEV_URL = "http://192.168.0.10:5173"  // ← 여기!
```

**PC IP 확인 방법:**

Windows:
```cmd
ipconfig
```

Mac/Linux:
```bash
ifconfig | grep "inet "
```

### 4. Vue.js 개발 서버 실행

Vue 프로젝트 폴더에서:

```bash
npm run dev -- --host
```

출력된 Network 주소를 MainActivity.kt에 입력하세요.

### 5. 앱 실행

```
상단 툴바에서 에뮬레이터/기기 선택
→ ▶ Run 버튼 클릭
```

## 📁 프로젝트 구조

```
VueTabletApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/yourcompany/vueapp/
│   │   │   ├── MainActivity.kt              # WebView 설정
│   │   │   ├── MyApplication.kt             # Firebase 초기화
│   │   │   ├── WebAppInterface.kt           # JS ↔ Android 브릿지
│   │   │   └── MyFirebaseMessagingService.kt # Push 알림
│   │   ├── res/
│   │   │   ├── layout/activity_main.xml
│   │   │   ├── values/
│   │   │   └── xml/network_security_config.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── google-services.json ⚠️ (Firebase에서 다운로드!)
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## 💡 주요 기능

### Vue에서 Android 기능 사용

```javascript
// Toast 메시지
window.Android.showToast("Hello!");

// 디바이스 정보
const info = JSON.parse(window.Android.getDeviceInfo());
console.log(info.model, info.isTablet);

// FCM 토큰
const token = window.Android.getFCMToken();

// 진동
window.Android.vibrate(200);

// 로그
window.Android.log("Debug message");
```

### Vue 컴포넌트 예시

```vue
<template>
  <button @click="testAndroid">테스트</button>
</template>

<script>
export default {
  methods: {
    testAndroid() {
      if (window.Android) {
        window.Android.showToast("앱에서 실행 중!");
      } else {
        console.log("웹 브라우저에서 실행 중");
      }
    }
  },
  
  mounted() {
    if (window.Android) {
      const info = JSON.parse(window.Android.getDeviceInfo());
      console.log("Device:", info);
    }
  }
}
</script>
```

## 🐛 디버깅

### Chrome DevTools 사용

1. Chrome에서 `chrome://inspect` 접속
2. 연결된 기기의 WebView 확인
3. "inspect" 클릭

### Logcat 필터

```
Android Studio → Logcat
- "WebView Console" → JavaScript 로그
- "FCM" → Push 알림 로그
- "WebAppInterface" → 네이티브 통신 로그
```

## ⚠️ 문제 해결

### localhost 연결 안 됨
- PC와 기기가 같은 Wi-Fi에 있는지 확인
- 방화벽에서 포트 5173 허용
- MainActivity.kt의 IP 주소 확인
- Vue 서버가 `--host` 옵션으로 실행되었는지 확인

### 빌드 오류
```
Build → Clean Project
Build → Rebuild Project
File → Invalidate Caches → Invalidate and Restart
```

### google-services.json 오류
- 파일이 `app/` 폴더에 있는지 확인
- 패키지 이름이 `com.yourcompany.vueapp`인지 확인
- Gradle Sync 실행

### WebView 흰 화면
- Logcat에서 에러 확인
- URL이 올바른지 확인
- 네트워크 연결 확인

## 📱 Push 알림 테스트

### FCM 토큰 확인

Logcat에서 "FCM Token" 검색 또는:

```javascript
const token = window.Android.getFCMToken();
console.log(token);
```

### 테스트 메시지 전송

```
Firebase Console → Cloud Messaging
→ "Send your first message"
→ 제목/내용 입력
→ "Send test message"
→ FCM 토큰 입력
→ Test
```

## 🎨 커스터마이징

### 패키지 이름 변경

1. AndroidManifest.xml에서 `package="com.yourcompany.vueapp"` 수정
2. app/build.gradle에서 `namespace` 및 `applicationId` 수정
3. 폴더 구조 변경: `java/com/yourcompany/vueapp/`
4. 모든 Kotlin 파일의 `package` 선언 수정

### 앱 이름 변경

`app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">내 앱 이름</string>
```

### 화면 방향 변경

AndroidManifest.xml:
```xml
<!-- 세로 모드 -->
android:screenOrientation="portrait"

<!-- 가로 모드 -->
android:screenOrientation="landscape"

<!-- 자동 회전 -->
android:screenOrientation="sensor"
```

## 📦 APK 빌드

### Debug APK
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```
위치: `app/build/outputs/apk/debug/`

### Release APK
```
Build → Generate Signed Bundle / APK
→ APK → Create new keystore
→ Release → Finish
```

## 🔧 개발 워크플로우

```bash
# 1. Vue 개발 서버 실행
cd your-vue-project
npm run dev -- --host

# 2. Android Studio에서 앱 실행
# → Vue 코드 수정 시 자동 새로고침!

# 3. 디버깅
# Chrome DevTools 또는 Logcat 사용
```

## 📚 추가 리소스

- [Android WebView 문서](https://developer.android.com/guide/webapps/webview)
- [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging)
- [Vue.js 문서](https://vuejs.org/)

## ⚙️ 요구사항

- Android Studio Arctic Fox 이상
- JDK 17
- Android SDK 24 이상 (Android 7.0+)
- Firebase 프로젝트
- Node.js 및 npm (Vue.js 개발용)

## 📄 라이선스

MIT License
