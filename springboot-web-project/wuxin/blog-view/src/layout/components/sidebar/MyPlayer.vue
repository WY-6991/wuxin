<template>
  <div>
    <aplayer :audio="audio" :lrcType="3" fixed />
  </div>
</template>
<script>
export default {
  name: 'MyPlayer',
  data() {
    // https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202111/20211125101719.png

    return {
      audio: [{
        name: '关山晓',
        artist: '无心001',
        url: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202110/20211001124817.mp3',
        cover: 'https://p1.music.126.net/n72JJkPg2-ENxhB-DsZ2AA==/109951163115400390.jpg?param=300y300', // prettier-ignore
        lrc: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202110/20211001124817.mp3',
        type: 'customHls',
      },
        {
          name: '关山晓1',
          artist: '无心001',
          url: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202110/20211004114952.mp3',
          cover: 'https://p1.music.126.net/n72JJkPg2-ENxhB-DsZ2AA==/109951163115400390.jpg?param=300y300', // prettier-ignore
          lrc: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202110/20211001124817.mp3',
          type: 'customHls',
        },
        {
          name: '关山晓2',
          artist: '无心001',
          url: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202110/20211001124817.mp3',
          cover: 'https://p1.music.126.net/n72JJkPg2-ENxhB-DsZ2AA==/109951163115400390.jpg?param=300y300', // prettier-ignore
          lrc: 'https://cdn.jsdelivr.net/gh/WY-6991/wuxin/img/202110/20211001124817.mp3',
          type: 'customHls',
        },]

    };
  },

}
</script>
<style scoped>

</style>