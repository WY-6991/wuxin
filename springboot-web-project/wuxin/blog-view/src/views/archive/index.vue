<template>
  <div class="ui attached segment timeline " :style="bgColor">

    <Title :title="`当前共收录文章${count}篇`"></Title>
    <div class="ui left icon input" style="position: absolute;right: 10px;">
      <input type="text" placeholder="Search..." v-model="keywords">
    </div>
    <div v-for="(archive,index) in archiveMap" :key="`archiveMap-${index}`" style="margin-top: 66px;">
      <div class="ui horizontal divider">
        <label class="ui large label " :class="colorObj[index%10]" style="color: white">{{
            archive.archiveTitle
          }}</label>
      </div>
      <div class="ui  message" :style="bgColor" v-for="(item,i) in archive.archiveList" :key="i">
        <i class="ui icon left bookmark"></i>

        <router-link :style="bgColor" :to="`/blog/${item.blogId}`" class="m-link" v-if="item.type===1 ">
          {{ item.title }}
        </router-link>
        <a :href="item.url" :style="bgColor" class="m-link" v-if="item.type===2" target="_blank">{{ item.title }}</a>
        <div class="ui label olive basic" v-if="item.type===1" style="float: right">原创</div>
        <div class="ui label orange basic" v-else style="float: right">转载</div>
      </div>
    </div>
<!--    <MyPagination-->
    <!--        :total-page="totalPage"-->
    <!--        :get-list="getArchives"-->
    <!--        -->
    <!--    />-->
  </div>


</template>
<script>
import Title from "@/components/common/Title";
import {getArchiveList} from "@/api/archive";
import {setTotalPage} from "@/utils/validate";

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
      count: 0,
      totalPage: 0,
      query: {
        current: 1,
        limit: 10,
        keywords: ''
      }
    }
  },

  created() {
    this.getArchives()
  }
  ,
  methods: {
    getArchives() {
      getArchiveList(this.query).then((res) => {
        if (res.code === 200) {
          const {count, page} = res
          this.count = count
          this.archiveMap = page.records
          this.totalPage = setTotalPage(page.total, page.size)
        }
      });
    }
    ,
    toBlog(blogId) {
      this.$router.push(`/blog/${blogId}`)
    }


  }

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