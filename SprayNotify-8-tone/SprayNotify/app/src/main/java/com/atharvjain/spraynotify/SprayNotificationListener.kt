package com.atharvjain.spraynotify

import android.app.Notification
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.os.Bundle
import java.util.concurrent.ConcurrentHashMap

class SprayNotificationListener : NotificationListenerService() {
    private val counts = ConcurrentHashMap<String, Int>()
    private var player: MediaPlayer? = null

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pkg = sbn.packageName
        if (pkg == packageName) return
        // Start with common messaging apps. Add/remove package IDs here as desired.
        val supported = setOf("com.whatsapp", "org.telegram.messenger", "com.google.android.apps.messaging", "com.instagram.android")
        if (pkg !in supported) return

        val n = sbn.notification ?: return
        if ((n.flags and Notification.FLAG_GROUP_SUMMARY) != 0) return
        val extras: Bundle = n.extras
        val title = extras.getString(Notification.EXTRA_TITLE)?.trim().orEmpty()
        val conversation = extras.getString(Notification.EXTRA_CONVERSATION_TITLE)?.trim().orEmpty()
        val key = pkg + "|" + if (conversation.isNotEmpty()) conversation else title
        if (key.endsWith("|")) return

        val number = (counts[key] ?: 0) + 1
        counts[key] = number
        playSpray(((number - 1) % 8) + 1)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // Removing/clearing a notification is treated as a reset for that chat.
        val n = sbn.notification ?: return
        val extras = n.extras
        val title = extras.getString(Notification.EXTRA_TITLE)?.trim().orEmpty()
        val conversation = extras.getString(Notification.EXTRA_CONVERSATION_TITLE)?.trim().orEmpty()
        val key = sbn.packageName + "|" + if (conversation.isNotEmpty()) conversation else title
        if (!key.endsWith("|")) counts.remove(key)
    }

private fun playSpray(number: Int) {
        val resId = resources.getIdentifier("spray$number", "raw", packageName)

            if (resId == 0) return

                player?.release()
                    player = null

                        try {
                                    val afd = resources.openRawResourceFd(resId) ?: return

                                            val mp = MediaPlayer()

                                                    // Route spray sound through Android's Notification audio stream
                                                            mp.setAudioAttributes(
                                                                            AudioAttributes.Builder()
                                                                                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                                                                                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                                                                                                            .build()
                                                            )

                                                                    mp.setDataSource(
                                                                                    afd.fileDescriptor,
                                                                                                afd.startOffset,
                                                                                                            afd.length
                                                                    )

                                                                            afd.close()

                                                                                    mp.setOnCompletionListener {
                                                                                                    it.release()

                                                                                                                if (player === it) {
                                                                                                                                    player = null
                                                                                                                }
                                                                                    }

                                                                                            mp.prepare()
                                                                                                    player = mp
                                                                                                            mp.start()

                        } catch (e: Exception) {
                                    e.printStackTrace()
                                            player = null
                        }
}
                        }
                                                                                                                }
                                                                                    }
                                                                    )
                                                            )
                        }
}


    override fun onDestroy() { player?.release(); player = null; super.onDestroy() }
}
