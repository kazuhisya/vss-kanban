<template>
  <tr style="cursor: pointer;" @click.stop="openEditDialog">
    <td class="drag-item">
      <span class="icon">
        <i class="fa fa-arrows"></i>
      </span>
    </td>
    <td class="large">{{row.name}}</td>
    <td>{{row.loginId}}</td>
    <td class="has-text-centered">{{authority}}</td>
    <td>
      <a class="button is-danger is-small" @click.stop="deleteRow">
        <span class="icon is-small">
          <i class="fa fa-times"></i>
        </span>
      </a>
    </td>
  </tr>
</template>

<script>

  import Utils from '../../utils'

  export default {
    name: 'user-list-row',
    props: ["row"],
    methods: {
      openEditDialog(e){
        const self = this;
        self.$emit("OpenEditDialog", e, self.row.id);
      },
      deleteRow(e){
        const self = this;
        if(!window.confirm("ユーザ「" + self.row.name + "」を削除します。本当によろしいですか？")) {
            return;
        }

        const param = {
          userId:self.row.id,
          lockVersion:self.row.lockVersion
        };

        Utils.setAjaxDefault();
        $.ajax({
          data: param,
          method: 'POST',
          url: "/user/delete"
        }).then(
          function (data) {
            //エラーが存在する場合、alert表示
            if(Utils.alertErrorMsg(data)) {
              return;
            }

            Utils.viewInfoMsg(data);
            setTimeout(function(){
              self.$emit("Refresh", e);
            },1500);
          }
        );
      }
    },
    computed: {
      authority() {
        const self = this;
        return self.row.authority === '1' ? '◯' : '';
      }
    }
  }

</script>
