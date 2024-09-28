package com.cyk.rabbitmq.test

class Solution {

    private val listRes = mutableListOf<List<Int>>()
    private val list = mutableListOf<Int>()
    fun subset(nums: IntArray): List<List<Int>> {
        nums.sort()
        val used = Array(nums.size) { false }
        dfs(nums, 0, used)
        return listRes
    }

    private fun dfs(nums: IntArray, start: Int, used: Array<Boolean>) {
        if (list.size >= 3) {
            listRes.add(ArrayList(list))
        }
        if(start >= nums.size) return

        for(i in start until nums.size) {
            if(i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue
            used[i] = true
            list.add(nums[i])
            dfs(nums, i + 1, used)
            used[i] = false
            list.removeLast()
        }
    }

    fun valid(arr2: List<List<Int>>): Int {
        var count = 0

        for (arr in arr2) { // Java 里面 for( : )
            val map = mutableMapOf<Int, Int>()
            for (i in arr) {
                map.put(i, map.getOrDefault(i, 0) + 1)
            }
            if (map.size > 2) continue
            val a = Array(2) { 0 }
            var i = 0
            for ((k, v) in map) {
                a[i++] = v
            }
            val r1 = a[0]
            val r2 = a[1]
            if (r1 == 1 && r2 > 1) {
                count++
            } else if (r2 == 1 && r1 > 1) {
                count++
            }
        }
        return count
    }


}

fun main() {
    val s = Solution()
    val arr = intArrayOf(1,1,4,5,1,4)
    //得到 >= 3 的全排列
    val result1 = s.subset(arr)
    println(result1)
    //找到有效的
    val result2 = s.valid(result1)

    println(result2)

}

