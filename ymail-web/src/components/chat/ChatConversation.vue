<template>
    <div class="chat-conversation">
        <div class="chat-conversation__messages">
            <ChatMessage v-for="(message, index) in messages" :key="index" :text="message.text" :author="message.author" />
        </div>
        <ChatLoading v-if="isLoading" />
    </div>
</template>

<script>
import ChatMessage from '@/components/chat/ChatMessage.vue';
import ChatLoading from '@/components/chat/ChatLoading.vue';

export default {
  name: 'ChatConversation',
  components: {
    ChatMessage,
    ChatLoading
  },
  props: {
    messages: {
      type: Array,
      default: () => []
    },
    isLoading: {
      type: Boolean,
      default: false
    }
  }
};
</script>

<style  scoped>
.chat-conversation {
  display: flex;
  padding: 15px;
  flex-direction: column;
  flex-grow: 1;
  overflow: auto;
}
.chat-conversation .chat-conversation__messages {
  display: flex;
  margin-top: auto;
  flex-direction: column;
}
.chat-conversation .chat-conversation__messages :deep(.chat-message):last-child {
  margin-bottom: 0;
}
</style>