# Balamitra (à°¬à°¾à°²à°®à°¿à°¤à±à°°)
### Empowering Anganwadi Workers with Empathy, Voice, and On-Device Intelligence

> *"The first six years donâ€™t just build a childâ€™s bodyâ€”they build their entire future. Yet across thousands of rural hamlets, the women guarding this critical window spend more hours filling out paper registers than holding a childâ€™s hand. Balamitra was born to change that."*

---

## ðŸŒ¸ Why Balamitra Matters

In almost every village and urban ward in India, an **Anganwadi worker (*Akka* or *Didi*)** is the heartbeat of child care. She monitors immunization, ensures nutritious meals, checks growth, and councils young parents. But her reality is overwhelming:
- She tracks 30 to 50 children single-handedly.
- Early developmental delays or silent malnutrition often hide until it is too late.
- Translating clinical growth terms like *"Z-score < -2 SD"* or *"Severe Stunting"* to an anxious, illiterate mother often creates shame, fear, or confusion instead of constructive care.
- Intermittent power and zero internet connectivity mean cloud-only tools fail where they are needed most.

**Balamitra** is designed from the ground up not as another cold data-entry tool, but as a compassionate digital companion sitting quietly in the worker's pocket. It respects her time, speaks her language, and turns raw measurements into empathetic, actionable care.

---

## ðŸ§  Core LLM & Intelligent Architecture

Balamitra integrates on-device and edge language models to solve problems that calculators and basic forms never could:

### 1. Empathetic Parent Counseling (De-stigmatizing Clinical Data)
Telling a mother that her child is "moderately malnourished" can feel like an accusation. Balamitraâ€™s LLM engine translates complex clinical indicators into warm, culturally grounded counseling dialogues in the motherâ€™s native language (Telugu, Hindi, or English):
- Explains *why* the child needs extra attention without inducing panic.
- Suggests practical, locally accessible food choices (like adding a spoonful of groundnut powder or jaggery to daily dalia) rather than unaffordable commercial supplements.
- Generates scripts that the worker can read or adapt during home visits to build trust with families.

### 2. "Connect the Dots" Longitudinal Synthesis
A drop in weight isn't just a numberâ€”it might be tied to a recent bout of diarrhea, missing mid-day meals during harvest season, or delayed motor milestones. Balamitraâ€™s narrative engine connects observations across weeks:
- Surfaces the story behind the data: *"Ananya gained height but plateaued in weight over the last 45 days. She is also hesitating on motor activitiesâ€”recommend checking home iron intake."*
- Enables the worker to answer: *"What really changed for this child?"* in seconds.

### 3. Hands-Free Conversational Voice Intake
An Anganwadi worker rarely has two hands free while weighing a wriggling toddler or supervising lunchtime. With Balamitra, she simply speaks naturally:
> *"à°ªà±‡à°°à± à°°à°®à±‡à°·à±, à°µà°¯à°¸à±à°¸à± 3 à°¸à°‚à°µà°¤à±à°¸à°°à°¾à°²à±, à°¬à°°à±à°µà± 12.8 à°•à°¿à°²à±‹à°²à±, à°¤à°‚à°¡à±à°°à°¿ à°°à°¾à°®à±, à°¤à°²à±à°²à°¿ à°²à°•à±à°·à±à°®à°¿..."*
> *(Name Ramesh, age 3 years, weight 12.8 kg, father Ramu, mother Lakshmi...)*

Our multi-lingual tokenization and NLU automatically extracts child names, family context, age, gender, and anthropometric readings, pre-filling the records instantly.

### 4. Privacy-First & Low-Bandwidth Respect
Every child's health record belongs to their community, not public cloud servers. Balamitra operates offline on local SQLite, ensuring zero latency in remote areas and ultra-lean sync packets (<5 KB) when occasional connectivity is found.

---

## âœ¨ Features at a Glance

| Feature | The Human Impact |
| :--- | :--- |
| **Voice-First Admission** | 10 seconds of natural speech replaces 5 minutes of tedious typing and manual paper entry. |
| **WHO Growth Radar** | Instant, visual health bands (Normal, Moderate Risk, Severe) remove mathematical guesswork. |
| **Developmental Milestones** | Gentle checklists tracking motor, cognitive, and social milestones so early delays are spotted in time. |
| **Counseling Guides** | Pre-generated, respectful dialogues for home visits with parents. |
| **Offline Reliability** | Works seamlessly inside remote Anganwadi centers with zero network connection. |
| **Localized Interface** | Built for workers in Bachupally and beyond with clear Telugu terminology and intuitive touch targets. |

---

## ðŸ› ï¸ Built With

- **Language & Framework**: Kotlin 2.0 & Jetpack Compose (Material 3)
- **Local Database**: Room (SQLite) with encrypted local persistence
- **Speech & NLU**: Android Speech Recognition + On-device multi-lingual regex tokenization (Telugu, Hindi, English)
- **Growth Standard**: WHO Child Growth Standards (Height-for-Age, Weight-for-Age, Weight-for-Height)
- **Architecture**: Clean Architecture (MVI / MVVM), offline-first repository pattern

---

## ðŸ¤ The Vision

Technology succeeds only when it empowers the person closest to the problem. Balamitra is built to give every Anganwadi worker the dignity, time, and intelligent support she deservesâ€”so that no childâ€™s growth story goes unnoticed.