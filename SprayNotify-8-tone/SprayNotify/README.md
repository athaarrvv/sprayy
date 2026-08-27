# Spray Notify

Android app that uses NotificationListenerService to count notifications per app/chat and play spray1..spray8 in a loop.

## Build
Open this folder in Android Studio (Ladybug or newer recommended), let Gradle sync, then Build > Build APK(s).

## Install/setup
1. Install the APK.
2. Open Spray Notify.
3. Tap **Open Notification Access**.
4. Enable **Spray Notify**.
5. Allow notification permission if Android asks.

## Sounds
Replace `app/src/main/res/raw/spray1.wav` through `spray8.wav` with your own short sounds. Keep the exact filenames and WAV format.

## Current behavior
- Supports WhatsApp, Telegram, Google Messages and Instagram.
- Counts per `app + conversation/title`.
- Notification 1→spray1, 2→spray2, …, 8→spray8, 9→spray1, then the sequence repeats.
- Clearing/removing a notification resets that chat's counter.

## Important limitation
Android notification data differs by app/version. Some apps expose a conversation title, others expose only the sender/title. Also, removing a notification is not identical to opening/reading a chat, so this first version uses notification removal as the reset signal.
