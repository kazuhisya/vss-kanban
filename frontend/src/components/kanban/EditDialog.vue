<template>
  <div class="modal" id="kanban-edit-dialog">
    <div class="modal-background"></div>
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">かんばん{{buttonLabel}}</p>
        <button class="delete" @click="closeDialog"></button>
      </header>
      <section class="modal-card-body">

        <article class="message is-danger" v-if="msg.globalErrMsg !== ''">
          <div class="message-body" v-html="msg.globalErrMsg"></div>
        </article>

        <div>
          <label class="label">かんばんタイトル <span class="tag is-danger">必須</span></label>
          <p class="control">
            <input class="input" type="text" v-model="form.kanbanTitle" placeholder="かんばんのタイトルを入力してください" id="kanban-edit-dialog-kanban-title">
            <span class="help is-danger" v-html="msg.kanbanTitleErrMsg"></span>
          </p>
        </div>

        <div>
          <label class="label">かんばん説明</label>
          <p class="control">
            <textarea v-model="form.kanbanDescription" placeholder="かんばんの説明を入力してください" class="textarea"></textarea>
            <span class="help is-danger" v-html="msg.kanbanDescriptionErrMsg"></span>
          </p>
        </div>

      </section>
      <footer class="modal-card-foot">
        <a class="button is-info" @click="saveDialog">
          <span class="icon is-small">
            <i class="fa fa-floppy-o"></i>
          </span>
          <span>{{buttonLabel}}</span>
        </a>
        <a class="button" @click="closeDialog">Cancel</a>
      </footer>
    </div>
  </div>
</template>

<script>

  import autosize from 'autosize/dist/autosize'
  import Utils from '../../utils'

  export default {
    name: 'kanban-edit-dialog',
    data() {
      return {
        form:{
          id:"",
          lockVersion:"0",
          kanbanTitle:"",
          archiveStatus:"0",
          kanbanDescription:""
        },
        msg:{
          globalErrMsg:"",
          kanbanTitleErrMsg:"",
          kanbanDescriptionErrMsg:"",
        },
        clearMsg(){
          this.msg.globalErrMsg = "";
          this.msg.kanbanTitleErrMsg = "";
          this.msg.kanbanDescriptionErrMsg = "";
        }
      }
    },
    methods: {
      openEditDialog() {
        const self = this;
        self.form.id = "";
        self.form.lockVersion = "0";
        self.form.kanbanTitle = "";
        self.form.archiveStatus = "0";
        self.form.kanbanDescription = "";
        self.clearMsg();
        Utils.openDialog('kanban-edit-dialog');

        setTimeout(function(){
          $('#kanban-edit-dialog-kanban-title').focus();
          const ta = document.querySelector('#kanban-edit-dialog textarea');
          autosize(ta);
          autosize.update(ta);
        }, 500);
      },
      closeDialog() {
        Utils.closeDialog('kanban-edit-dialog');
      },
      saveDialog(e) {
        const self = this;
        Utils.setAjaxDefault();
        $.ajax({
          data: self.form,
          method: 'POST',
          url: "/kanban/create"
        }).then(
          function (data) {
            if(Utils.writeErrorMsg(self, data)) {
              return;
            }
            Utils.viewInfoMsg(data);
            setTimeout(function(){
              self.clearMsg();
              self.closeDialog();
              self.$emit("ViewKanbanMain", e, data.result.kanbanId);
            },1500);
          }
        );
      }

    },
    computed: {
      buttonLabel() {
        const self = this;
        return self.form.id === "" ? '登録' : '変更';
      },
    }
  }

</script>
