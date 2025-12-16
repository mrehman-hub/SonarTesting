package com.example.smartcomdemo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import java.lang.Runtime

class MemoryHogActivity : Activity() {
    private val TAG = "MemoryHog"
    private var job: Job? = null
    private val allocated = mutableListOf<ByteArray>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // UI not required; just start hogging
        job = CoroutineScope(Dispatchers.Default).launch {
            try {
                while (isActive) {
                    // Allocate 4 MB chunks — change size to adjust pressure
                    val chunk = ByteArray(4 * 1024 * 1024)
                    allocated.add(chunk)
                    val rt = Runtime.getRuntime()
                    val usedMB = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024)
                    val freeMB = rt.freeMemory() / (1024 * 1024)
                    Log.w(TAG, "Allocated chunk. UsedMB=$usedMB freeMB=$freeMB totalMB=${rt.totalMemory()/(1024*1024)}")
                    delay(500) // slow down allocations so you can observe
                }
            } catch (t: Throwable) {
                Log.e(TAG, "Allocation stopped by: $t")
            }
        }
    }

    override fun onDestroy() {
        job?.cancel()
        allocated.clear()
        super.onDestroy()
    }
}