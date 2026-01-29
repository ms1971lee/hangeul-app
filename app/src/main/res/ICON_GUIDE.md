# 앱 아이콘 설정 가이드

## 아이콘 위치

앱 아이콘은 다음 폴더에 배치해야 합니다:

```
app/src/main/res/
├── mipmap-hdpi/
│   ├── ic_launcher.png (72x72)
│   └── ic_launcher_round.png (72x72)
├── mipmap-mdpi/
│   ├── ic_launcher.png (48x48)
│   └── ic_launcher_round.png (48x48)
├── mipmap-xhdpi/
│   ├── ic_launcher.png (96x96)
│   └── ic_launcher_round.png (96x96)
├── mipmap-xxhdpi/
│   ├── ic_launcher.png (144x144)
│   └── ic_launcher_round.png (144x144)
└── mipmap-xxxhdpi/
    ├── ic_launcher.png (192x192)
    └── ic_launcher_round.png (192x192)
```

## 임시 방법 (빌드 에러 해결)

Android Studio는 기본 아이콘을 자동 생성합니다.
아이콘이 없어서 빌드 에러가 나면:

1. **Android Studio에서 자동 생성:**
   ```
   res 폴더 우클릭 → New → Image Asset
   → Launcher Icons (Adaptive and Legacy)
   → Foreground Layer: 이미지 또는 색상 선택
   → Next → Finish
   ```

2. **온라인 아이콘 생성기 사용:**
   - https://www.appicon.co/
   - https://icon.kitchen/
   - 1024x1024 이미지 업로드
   - 모든 크기 다운로드
   - 해당 폴더에 복사

## 추천 사이즈

- **원본 이미지:** 1024x1024 PNG (투명 배경)
- **최소 사이즈:** 512x512

## 아이콘 디자인 가이드

- 심플하고 명확한 디자인
- 테두리 여백 10% 유지
- 배경색은 브랜드 색상 사용
- 흰색/검은색 배경에서 모두 테스트

## 빌드 진행 방법

아이콘이 없어도 앱은 빌드되지만, 기본 Android 아이콘이 표시됩니다.
나중에 아이콘을 교체해도 됩니다.

현재는 Android Studio의 기본 아이콘이 자동 생성되므로
별도 설정 없이 바로 빌드 가능합니다.
