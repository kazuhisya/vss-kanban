<template>
  <div v-if="fileList.length > 0" class="box">
    <article class="media">
      <div class="media-content">
        <file-item v-for="(item, index) in fileList" :item="item" :index="index" :type="type" :key="item.attachmentFileId" :groupName="createDataLightboxGroup" @DeleteItem="deleteItem"></file-item>
      </div>
    </article>
  </div>
</template>

<script>
  import Utils from '../../utils'
  import FileItem from './FileItem'

  export default {
    name: 'file-list',
    props:["fileList", "type"],
    components: {
      'file-item': FileItem
    },
    methods:{
      deleteItem(e, index) {
        const self = this;
        self.$emit("DeleteItem", e, index);
      }
    },
    computed: {
      createDataLightboxGroup() {
        return Utils.getUniqueStr();
      }
    }
  }
</script>
