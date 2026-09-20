# Balamitra (à°¬à°¾à°²à°®à°¿à°¤à±à°°)
### AI-Powered Offline Early Childhood Development & Growth Monitoring Platform for Anganwadi Workers

**Balamitra** is an intelligent, low-bandwidth, privacy-first Android application purpose-built for Anganwadi workers (AWW) in rural and peri-urban centers. It streamlines child enrollment, milestone tracking, real-time WHO growth assessments, and nutritional meal monitoring completely on-device without continuous internet dependency.

---

## ðŸŒŸ Key Capabilities

1. **Natural Multilingual Voice Enrollment (Telugu / Hindi / English)**
   - Hands-free voice recognition for rapid child admissions.
   - Extracts Child Name, Age, Gender, Weight, Height, Parents' Names, and Ward directly from spoken Telugu (*"à°ªà±‡à°°à± à°°à°®à±‡à°·à±, à°µà°¯à°¸à±à°¸à± 3 à°¸à°‚à°µà°¤à±à°¸à°°à°¾à°²à±, à°¬à°°à±à°µà± 12.8 à°•à°¿à°²à±‹à°²à±..."*), Hindi (*"à¤¨à¤¾à¤® à¤ªà¥à¤°à¤¿à¤¯à¤¾, à¤‰à¤®à¥à¤° 3 à¤¸à¤¾à¤², à¤µà¤œà¤¨ 12 à¤•à¤¿à¤²à¥‹..."*), or English.
   - 1-tap quick presets and manual edit fallback.

2. **WHO Standardized Growth Engine**
   - Instant Z-score computation for Stunting (Height-for-Age), Underweight (Weight-for-Age), and Wasting (Weight-for-Height).
   - Clear visual health bands (Normal, Moderate Risk, Severe Acute Malnutrition - SAM).

3. **Offline-First & Ultra-Low Data Consumption**
   - Complete local Room SQLite database with transactional state.
   - Minimal telemetry overhead (< 5 KB per sync packet) designed for 2G/3G intermittent connectivity in remote villages.
   - Fully functional when offline; batches changes to securely synchronize when data is available.

4. **Designed Specifically for Anganwadi Workers**
   - Real-time live date and time indicators.
   - Worker profile contextualized for local centers (e.g., *Bachupally Anganwadi Kendram, Medchal-Malkajgiri*).
   - Large touch targets, high contrast, clean typography, and localized Telugu terminology.

5. **ECD Milestone & Nutritional Tracking**
   - Tracks gross motor, fine motor, cognitive, and socio-emotional milestones.
   - Daily meal ration tracking and automated follow-up reminders for vulnerable children.

---

## ðŸ› ï¸ Architecture & Tech Stack

- **Platform**: Native Android (Min SDK 26, Target SDK 35)
- **Language**: Kotlin 2.0
- **UI Framework**: Jetpack Compose with Material 3
- **Local Persistence**: Room Database (SQLite) + Kotlin Coroutines & Flows
- **Voice Recognition**: Android SpeechRecognizer + Custom Offline Multi-Lingual Regex Tokenizer
- **Testing**: JUnit4 unit test suite covering domain growth engines, vaccination schedules, and voice parsers

---

## ðŸš€ Getting Started

### Prerequisites
- Android Studio Ladybug | 2024.2+ or Android SDK
- JDK 17+ (e.g. JetBrains Runtime `jbr`)

### Building from Source
```bash
# Clone the repository
git clone https://github.com/nithyahasinialle-rgb/Balamitra.git
cd Balamitra

# Run unit tests
./gradlew testDebugUnitTest

# Assemble debug APK
./gradlew assembleDebug
```
The compiled APK will be available at:
`app/build/outputs/apk/debug/app-debug.apk` or in the root directory as `Balamitra.apk`.

---

## ðŸ§ª Verification & Test Suite
All core domain engines and voice parsing rules are verified via unit tests:
- `DomainEnginesTest`: Tests WHO growth classifications, developmental milestone evaluators, and session defaults.
- `ChildAdmissionVoiceParserTest`: Verifies multilingual regex pattern matching and token extraction in Telugu, Hindi, and English.

---

## ðŸ“„ License
This project is open-source under the MIT License.