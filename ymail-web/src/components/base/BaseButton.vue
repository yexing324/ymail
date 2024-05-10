<template>
    <button
        class="base-button button flex justify-center items-center"
        :class="[
            colorClass,
            sizeClass,
            {
                'is-fullwidth': fullwidth,
                'is-rounded': rounded,
                'is-disabled': disabled,
                'is-icon': icon,
                'is-circle': circle,
                'is-active': active,
            }
        ]"
        :disabled="disabled"
        @click="onClickButtonDo">
        <span v-if="iconLeft" class="icon">
            <Icon :icon="iconLeft" :width="iconSize" :height="iconSize" :color="iconColor" />
        </span>
        <span v-if="!icon" class="is-flex">
            <slot></slot>
        </span>
        <span v-if="iconRight" class="icon ml-1">
            <Icon :icon="iconRight" :width="iconSize" :height="iconSize" :color="iconColor" />
        </span>
    </button>
</template>

<script>
import { computed, toRefs } from 'vue';
import { Icon } from '@iconify/vue';
import { useRouter } from 'vue-router';
import {
    buttonColorValidator as propUtilsButtonColorValidator,
    sizeValidator as propUtilsSizeValidator
} from '@/utils/propValidators';

export default {
    name: 'BaseButton',
    components: {
        Icon
    },
    props: {
        color: {
            type: String,
            default: 'default',
        },
        size: {
            type: String,
            default: 'normal',
        },
        fullwidth: {
            type: Boolean,
            default: false
        },
        iconLeft: {
            type: String,
            default: null
        },
        iconRight: {
            type: String,
            default: null
        },
        iconSize: {
            type: String,
            default: null,
        },
        iconColor: {
            type: String,
            default: null,
        },
        disabled: {
            type: Boolean,
            default: false
        },
        rounded: {
            type: Boolean,
            default: false
        },
        icon: {
            type: Boolean,
            default: false
        },
        circle: {
            type: Boolean,
            default: false
        },
        to: {
            type: [ Object, String ],
            default: null
        },
        active: {
            type: Boolean,
            default: false
        }
    },
    emits: [ 'click' ],
    setup( props, { emit } ) {
        const { color, size, to } = toRefs( props );
        const router = useRouter();

        const colorClass = computed( () => {
            return propUtilsButtonColorValidator( color.value ) ? `is-${color.value}` : 'is-default';
        } );
        const sizeClass = computed( () => {
            return propUtilsSizeValidator( size.value ) ? `is-${size.value}` : 'is-normal';
        } );

        const onClickButtonDo = function( e ) {
            emit( 'click', e );

            if ( to.value ) {
                typeof to.value === 'object' ? router.push( to.value ) : window.location.href = to.value;
            }
        };

        return {
            colorClass,
            sizeClass,
            onClickButtonDo
        };
    }
};
</script>

<style  scoped>
.base-button {
  padding: 5px 15px;
  transition: all 250ms ease-in-out;
  cursor: pointer;
}

.base-button.button.is-primary {
  background-color: skyblue;
  color: white;
  border-color: transparent;
  box-shadow: 0 20px 20px rgba(0, 0, 255, 0.08) !important;
}

.base-button.button.is-primary:hover {
  background-color: #998dea;
  color: #ffffff;
  border-color: transparent;
}

.base-button.button.is-primary:focus {
  color: blue;
  border-color: transparent;
}

.base-button.button.is-info {
  box-shadow: 0 20px 20px rgba(0, 0, 255, 0.08) !important;
}

.base-button.button.is-success {
  box-shadow: 0 20px 20px rgba(0, 0, 255, 0.08) !important;
}

.base-button.button.is-warning {
  box-shadow: 0 20px 20px rgba(0, 0, 255, 0.08) !important;
}

.base-button.button.is-danger {
  box-shadow: 0 20px 20px rgba(0, 0, 255, 0.08) !important;
}

.base-button.button.is-light {
  box-shadow: 0 20px 20px rgba(0, 0, 255, 0.08) !important;
}

.base-button.button.is-dark {
  box-shadow: 0 20px 20px rgba(0, 0, 255, 0.08) !important;
}

.base-button.button.is-large {
  height: 56px;
  font-size: 16px;
}

.base-button.button.is-medium {
  height: 48px;
  font-size: 15px;
}

.base-button.button.is-normal {
  height: 40px;
  font-size: 14px;
}

.base-button.button.is-large.is-icon {
  width: 56px;
}

.base-button.button.is-medium.is-icon {
  width: 48px;
}

.base-button.button.is-normal.is-icon {
  width: 40px;
}

.base-button.button.is-rounded {
  border-radius: 15px;
}

.base-button.button.is-circle {
  border-radius: 9999px !important;
}

</style>