package com.seank.commontestlib

object TestUtil {
    fun getHexString(int: Int): String {
        return "0x%08X".format(int)
    }
}