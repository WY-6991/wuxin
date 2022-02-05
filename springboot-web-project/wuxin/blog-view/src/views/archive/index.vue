<template>
  <div class="ui attached segment timeline " :style="$MyBg()">

    <Title :title="`当前共收录文章${total}篇`"></Title>
    <div class="ui left icon input" style="position: absolute;right: 10px;">
      <input type="text" placeholder="Search..." v-model="keywords">
    </div>
    <div v-for="(archive,index) in usersMap" :key="`archiveMap-${index}`" style="margin-top: 66px;">
      <div class="ui horizontal divider">
        <label class="ui large label " :class="colorObj[index%10]" style="color: white">{{
            archive.archiveTitle
          }}</label>
      </div>
      <div class="ui  message" :style="$MyBg()" v-for="(item,i) in archive.archiveList" :key="i">
        <i class="ui icon left bookmark"></i>

        <router-link :style="$MyBg()" :to="`/blog/${item.blogId}`" class="m-link" v-if="item.type===1 ">
          {{ item.title }}
        </router-link>
        <a :href="item.url" :style="$MyBg()" class="m-link" v-if="item.type===2" target="_blank">{{ item.title }}</a>
        <div class="ui label olive basic" v-if="item.type===1" style="float: right">原创</div>
        <div class="ui label orange basic" v-else style="float: right">转载</div>
      </div>
    </div>
  </div>
</template>
<script>
import Title from "@/components/common/Title";
import {getArchiveList} from "@/api/archive";
import {mapGetters} from "vuex";

export default {
  name: 'Archive',
  components: {Title},
  data() {
    return {
      archiveMap: [],
      colorObj: {
        0: 'bg-black',
        1: 'bg-red',
        2: 'bg-deepskyblue',
        3: 'bg-teal',
        4: 'bg-green',
        5: 'bg-cornflowerblue ',
        6: 'bg-orangered',
        7: 'bg-orange',
        8: 'bg-peru',
        9: 'bg-saddlebrown',
      },
      keywords: '',
    }
  },

  computed: {

    ...mapGetters(['settingState']),

    usersMap() {
      const alist = this.archiveMap
      const keywords = this.keywords
      // 基本逻辑 由于涉及到多级标题查询，使用filter有点复杂，可能有点难以实现（反正这里我是没有实现，反而出现了一堆bug）
      // 一级标题 如果查询关键词中在一级标题中 返回所有二级标题
      // 如果一级标题内容没有，但是二级标题含有一个或者多个关键词，返回对应关键词内容
      let list = []
      let list1 = []
      for (let i = 0; i < alist.length; i++) {
        // console.log(alist[i])
        let t1 = alist[i]['archiveTitle'].indexOf(keywords) !== -1
        if (t1) {
          list.push(alist[i])
        } else {
          let l2 = []
          // 如果一级标题中获取不到关键词内容遍历二级标题
          for (let j = 0; j < alist[i]['archiveList'].length; j++) {
            // 二级标题关键词是否存在判断条件
            let t2 = alist[i]['archiveList'][j]['title'].indexOf(keywords) !== -1
            if (t2) {
              l2.push(alist[i]['archiveList'][j])
            }
          }
          // 这里没有使用判断l2是不是为空 非空，如果不添加到list1中 ，后面获取不到索引，因此，空数据也要添加进去
          list1.push(l2)
        }
      }

      // 获取两个list长度
      let a = list.length
      let b = 0
      // 对二级标题遍历 去除长度不为0的数据
      for (let i = 0; i < list1.length; i++) {
        b += list1[i].length
        // if (list1[i].length !== 0) {
        //   let temp = alist[i]
        //   temp['archiveList'] = list1[i]
        //   list.push(temp)
        // }
      }

      // 标题全部为空 二级标全部为空
      if (a === 0 && b === 0) {
        return []
      }
      // 一级有标题 二级标题无内容
      if (a !== 0 && b === 0) {
        return list
      }
      // 一级标题标题无内容 二级标题有内容   上面将二级标题对应内容有数据直接添加到list中这里可以直接返回
      if (a === 0 && b !== 0) {
        for (let i = 0; i < list1.length; i++) {
          if (list1[i].length !== 0) {
            let temp = alist[i]
            temp['archiveList'] = list1[i]
            list.push(temp)
          }
        }
        return list
      }
      //  一级二标题均有内容
      // 上面判断如果一级标题含有关键词内容，直接跳对二级标题遍历，后面就算含有关键词也没有问题，添加到list中去了
      // 因此此处获取二级标题索引，让两者直接相加，然后去重就可了
      let newArr = []
      let newArr1 = []
      if (a !== 0 && b !== 0) {
        for (let i = 0; i < list1.length; i++) {
          if (list1[i].length !== 0) {
            // 如果list1中有数据对应内容索引内容标题一致，就是有重叠部分
            let temp = alist[i]
            temp['archiveList'] = list1[i]
            newArr1.push(temp)
          }
        }
        console.log(list)
        console.log(newArr1)
        // 如果两个数组相等
        if (list === newArr1) {
          console.log('两个数组相等....')
          return newArr1
        } else {

        }
        // 数组合并并且去重
      }


      // 其他情况返回null
      return []


    },

    total() {
      let sum = 0;
      const list = this.archiveMap
      for (let i = 0; i < list.length; i++) {
        sum += list[i]['archiveList'].length
      }
      return sum

    },


  },
  created() {
    this.getArchives()
  },
  methods: {
    getArchives() {
      getArchiveList().then((res) => {
        if (res.code === 200) {
          this.archiveMap = res.result;
        }
      });
    },
    toBlog(blogId) {
      this.$router.push(`/blog/${blogId}`)
    }


  },
}
</script>
<style scoped>

.timeline {
  min-height: 100vh !important;
  width: 100% !important;
}

.m-link {
  color: black;
  cursor: pointer;

}

.m-link:hover {
  color: blue;
}


.bg-black {
  background-color: black !important;
}

.bg-red {
  background-color: red !important;
}


.bg-deepskyblue {
  background-color: deepskyblue !important;
}

.bg-teal {
  background-color: teal !important;
}

.bg-green {
  background-color: green !important;
}

.bg-cornflowerblue {
  background-color: cornflowerblue !important;
}

.bg-orangered {
  background-color: orangered !important;
}

.bg-orange {
  background-color: orange !important;
}

.bg-peru {
  background-color: peru !important;
}

.bg-saddlebrown {
  background-color: saddlebrown !important;
}


</style>