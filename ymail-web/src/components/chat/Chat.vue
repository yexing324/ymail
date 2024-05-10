<style scoped>
.chat {
  display: flex;
  height: calc(100vh - 5rem - 0px);
  width: 500px;
  border: 1px solid skyblue;
  flex-direction: column;
  max-width: 90vw;
  border-radius: 10px;
  transition-property: box-shadow, opacity;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* 假设的值，需要替换为实际的$dark变量值 */
}

</style>

<template>
  <div class="chat mx-auto my-10">
    <ChatHeader/>
    <ChatConversation ref="messagesRef" :messages="messages" :isLoading="isLoading"/>
    <ChatBox @update:messages="onUpdateMessagesDo"/>
  </div>
</template>

<script>
import {ref, reactive, nextTick, onBeforeMount} from 'vue';
import ChatConversation from '@/components/chat/ChatConversation.vue';
import ChatHeader from '@/components/chat/ChatHeader.vue';
import ChatBox from '@/components/chat/ChatBox.vue';

export default {
  name: 'ChatBase',
  components: {
    ChatHeader,
    ChatBox,
    ChatConversation
  },

  setup() {
    let socket;let index=0;let lastIndex=0;
    onBeforeMount(() => {
      socket = new WebSocket('ws://localhost:10089');
      socket.onopen = () => {
        console.log('WebSocket连接成功');
      };
      let current;

      socket.onmessage = (event) => {

        const messageContent = event.data.replace(/message:/, "").trim();
        if(lastIndex===index-1){
          messages.push({
            text: messageContent,
            author: 'assistant'
          });
          current=messages[messages.length-1]
          lastIndex++;
        }else{
          current.text=messageContent
        }


        scrollToBottomMessages();
        // // 仅当消息不为空时才添加到列表中
        // if (messageContent) {
        //   this.messages.push(messageContent);
        // }
        //
        // // 自动滚动到最新的消息
        // this.$nextTick(() => {
        //   const messagesContainer = this.$el.querySelector('.messages');
        //   messagesContainer.scrollTop = messagesContainer.scrollHeight;
        // });
      };
    })


    const isLoading = ref(false);
    const messages = reactive([
      {
        text: '你好,我是你的邮件助手,有什么需要帮助的吗?',
        author: 'assistant'
      },
    ]);
    const messagesRef = ref(null);

    const onUpdateMessagesDo = (value) => {
      sendMessage(value);
    };

    const scrollToBottomMessages = async () => {
      await nextTick();
      const element = messagesRef.value;
      element.$el.scrollTo({top: element.$el.scrollHeight});
    };

    function sendSocketMessage(text) {
      if (socket && socket.readyState === WebSocket.OPEN) {
        // 发送用户输入的消息
        socket.send(text);
      } else {
        console.error('WebSocket未连接');
      }
    }




    const sendMessage = async (textValue) => {
      index++;
      lastIndex=index-1;
      messages.push({
        text: textValue,
        author: 'user'
      });

      scrollToBottomMessages();

      sendSocketMessage(textValue)
      //进行socket通信


      // const options = {
      //   method: 'POST',
      //   headers: {
      //     'Content-Type': 'application/json',
      //     Authorization: `Bearer 123`,
      //   },
      //   body: JSON.stringify( {
      //     model: 'text-davinci-003',
      //     prompt: textValue,
      //     temperature: 0.9,
      //     max_tokens: 150,
      //     top_p: 1.0,
      //     frequency_penalty: 0,
      //     presence_penalty: 0.6,
      //   } ),
      // };
      //
      try {
        isLoading.value = true;
        //
        // const response = await fetch(
        //   'https://api.openai.com/v1/completions',
        //   options
        // );
        // const data = await response.json();
        scrollToBottomMessages();

        isLoading.value = false;
      } catch ( error ) {
        console.error( error );

        isLoading.value = false;
      }
    };

    return {
      isLoading,
      messages,
      messagesRef,
      onUpdateMessagesDo
    };
  }
};
</script>

