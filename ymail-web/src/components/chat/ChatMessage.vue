<template>
    <div 
        class="chat-message"
        :class="{
            'is-user': isUser,
            'is-assistant': isAssistant,
        }">
        <div class="chat-message__avatar">
<!--            <AssistantLogoImage v-if="isAssistant" />-->
            <Icon icon="material-symbols:check-circle" width="20px" height="20px" v-if="isUser" />
        </div>
        <div class="chat-message__text">
            {{ text }}
        </div>
    </div>
</template>

<script>
import { computed, toRefs } from 'vue';
import { Icon } from '@iconify/vue';
// import AssistantLogoImage from '../../assets/assistant_logo.svg';

export default {
  name: 'ChatMessage',
  components: {
    // AssistantLogoImage,
    Icon
  },
  props: {
    text: {
      type: String,
      default: null
    },
    author: {
      type: String,
      default: 'assistant'
    }
  },

  setup( props ) {
    const { author } = toRefs( props );

    const isUser = computed( () => {
        return author.value === 'user';
    } );

    const isAssistant = computed( () => {
        return author.value === 'assistant';
    } );

    return {
      isUser,
      isAssistant
    };
  }
};
</script>

<style  scoped>
.chat-message {
  display: inline-flex;
  margin-bottom: 15px;
  align-items: flex-end;
}
.chat-message .chat-message__text {
  padding: 15px;
}
.chat-message.is-user {
  margin-left: auto;
}
.chat-message.is-user .chat-message__avatar {
  color: skyblue;
  order: 2;
  margin-left: 5px;
}
.chat-message.is-user .chat-message__text {
  background: skyblue;
  color: white;
  font-size: 14px;
  letter-spacing: 0.5px;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  border-bottom-left-radius: 15px;
  order: 1;
}
.chat-message.is-assistant {
  margin-right: auto;
}
.chat-message.is-assistant .chat-message__avatar {
  color: skyblue;
  order: 2;
  margin-left: 5px;
}
.chat-message.is-assistant .chat-message__avatar svg {
  width: 30px;
}
.chat-message.is-assistant .chat-message__text {
  background:#e4e6eb;
  color: black;
  font-size: 14px;
  letter-spacing: 0.5px;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
}

/*# sourceMappingURL=test.css.map */

</style>