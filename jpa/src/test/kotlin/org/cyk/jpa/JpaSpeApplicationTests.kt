package org.cyk.jpa

import org.cyk.jpa.model.Userinfo
import org.cyk.jpa.repo.UserRepoJpaSpe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import jakarta.annotation.Resource
import jakarta.persistence.criteria.Predicate

@SpringBootTest
class JpaSpeApplicationTests {

    @Resource
    private lateinit var userRepoJpaSpe: UserRepoJpaSpe

    //username 和 age 不为空才作为查询条件
    @Test
    fun test1() {
        //模拟外部输入
        val username: String? = ""
        val age: Int? = 21

        /**
         * 拼接查询条件
         * root: 代表实体对象，可以通过它获取属性值
         * cq: 用于生成SQL语句
         * cb: 用于拼接查询条件
         */
        val s = Specification<Userinfo> { root, cq, cb ->
            val predicates = mutableListOf<Predicate>()

            if (!username.isNullOrBlank()) {
                val p = cb.equal(root.get<String>("username"), username)
                predicates.add(p)
            }

            age?.let {
                val p = cb.equal(root.get<Int>("age"), it)
                predicates.add(p)
            }

            cb.and(*predicates.toTypedArray())
        }

        //查询
        userRepoJpaSpe.findAll(s).forEach(::println)
    }

    //查询 + 分页 + 排序（倒序）
    @Test
    fun test2() {
        //模拟外部输入
        val username: String? = ""
        val age: Int? = 21

        /**
         * 拼接查询条件
         * root: 代表实体对象，可以通过它获取属性值
         * cq: 用于生成SQL语句
         * cb: 用于拼接查询条件
         */
        val s = Specification<Userinfo> { root, cq, cb ->
            val predicates = mutableListOf<Predicate>()

            if (!username.isNullOrBlank()) {
                val p = cb.equal(root.get<String>("username"), username)
                predicates.add(p)
            }

            age?.let {
                val p = cb.equal(root.get<Int>("age"), it)
                predicates.add(p)
            }

            cb.and(*predicates.toTypedArray())
        }

        //分页 + 排序
        val pg = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("cTime"))) //注意这里对应的是成员变量名(而非表中的字段名)

        val result = userRepoJpaSpe.findAll(s, pg)
        result.forEach(::println)
    }

}
