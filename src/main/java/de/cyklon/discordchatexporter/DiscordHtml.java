package de.cyklon.discordchatexporter;

public final class DiscordHtml {

    private DiscordHtml() {}

    private static final String COMMENT = """
            <!--
            This transcript was generated using: https://github.com/mahtoid/DiscordChatExporterPy
            If you have any issues or suggestions - open an issue on the Github Repository or alternatively join discord.gg/2uhHBQDwcc        
            -->
           
           
            """;

    public static final String BASE = COMMENT + """                        
            <!DOCTYPE html>
            <html lang="en">
                        
            <head>
                <title>{{SERVER_NAME}} - {{CHANNEL_NAME}}</title>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                <meta name="viewport" content="width=device-width" />
                        
                <style>
                    @font-face {
                      font-family: gg sans;
                      font-weight: 400;
                      src: url(https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/ggsans-400.woff2) format("woff2")
                    }
                        
                    @font-face {
                      font-family: gg sans;
                      font-weight: 500;
                      src: url(https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/ggsans-500.woff2) format("woff2")
                    }
                        
                    @font-face {
                      font-family: gg sans;
                      font-weight: 600;
                      src: url(https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/ggsans-600.woff2) format("woff2")
                    }
                        
                    @font-face {
                      font-family: gg sans;
                      font-weight: 700;
                      src: url(https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/ggsans-700.woff2) format("woff2")
                    }
                        
                    @font-face {
                      font-family: gg sans;
                      font-weight: 800;
                      src: url(https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/ggsans-800.woff2) format("woff2")
                    }
                        
                    a {
                        text-decoration: none;
                    }
                        
                    a:hover {
                        text-decoration: underline;
                    }
                        
                    img {
                        object-fit: contain;
                    }
                        
                    .chatlog__markdown {
                        max-width: 100%;
                        line-height: 1.3;
                        overflow-wrap: break-word;
                    }
                        
                    .chatlog__markdown-preserve {
                        white-space: pre-wrap;
                    }
                        
                    .markdown {
                        max-width: 100%;
                        line-height: 1.3;
                        overflow-wrap: break-word;
                    }
                        
                    .preserve-whitespace {
                        white-space: pre-wrap;
                    }
                        
                    .spoiler {
                        display: inline-block;
                    }
                        
                    .spoiler--hidden {
                        cursor: pointer;
                        background-color: #202225;
                    }
                        
                    .spoiler-text {
                        border-radius: 3px;
                    }
                        
                    .spoiler--hidden .spoiler-text {
                        opacity: 0;
                    }
                        
                    .spoiler--hidden .spoiler-text::selection {
                        color: rgba(0, 0, 0, 0);
                    }
                        
                    .spoiler-image {
                        position: relative;
                        overflow: hidden;
                        border-radius: 3px;
                    }
                        
                    .spoiler--hidden .spoiler-image {
                        box-shadow: 0 0 1px 1px rgba(0, 0, 0, 0.1);
                    }
                        
                    .spoiler--hidden .spoiler-image * {
                        filter: blur(44px);
                    }
                        
                        .spoiler--hidden .spoiler-image:after {
                            content: "SPOILER";
                            color: #dcddde;
                            background-color: rgba(0, 0, 0, 0.6);
                            position: absolute;
                            left: 50%;
                            top: 50%;
                            transform: translate(-50%, -50%);
                            font-weight: 600;
                            padding: 100%;
                            border-radius: 20px;
                            letter-spacing: 0.05em;
                            font-size: 0.9em;
                        }
                        
                    .spoiler--hidden:hover .spoiler-image:after {
                        color: #fff;
                        background-color: rgba(0, 0, 0, 0.9);
                    }
                        
                    .quote {
                        margin: 0.1em 0;
                        padding-left: 0.6em;
                        border-left: 4px solid;
                        border-radius: 3px;
                    }
                        
                    .pre {
                        font-family: "Consolas", "Courier New", Courier, monospace;
                        padding: .2em;
                        margin: -.2em 0;
                        border-radius: 3px;
                        font-size: 85%;
                    }
                        
                    .pre--multiline {
                        margin-top: 0.25em;
                        padding: 0.5em;
                        border: 2px solid;
                        border-radius: 5px;
                    }
                        
                    .pre--inline {
                        padding: 2px;
                        border-radius: 3px;
                        font-size: 0.85em;
                    }
                        
                    .unix-timestamp {
                        background: #40444b;
                        border-radius: 3px;
                        padding: 0 2px;
                    }
                        
                    .mention {
                        border-radius: 3px;
                        padding: 0 2px;
                        color: #dee0fc;
                        background: rgba(88, 101, 242, .3);
                        font-weight: 500;
                    }
                        
                    .mention:hover {
                        background: rgba(88, 101, 242, .6);
                    }
                        
                    .emoji {
                        width: 1.25em;
                        height: 1.25em;
                        margin: 0 0.06em;
                        vertical-align: -0.4em;
                    }
                        
                    .emoji--small {
                        width: 1em;
                        height: 1em;
                    }
                        
                    .emoji--large {
                        width: 2.8em;
                        height: 2.8em;
                    }
                        
                    /* Chatlog */
                        
                    .chatlog {
                        padding: 1rem 5px 0.125rem 2px;
                        border-top: 1px solid rgba(255, 255, 255, 0.1);
                    }
                        
                    .chatlog__followup-symbol {
                        grid-column: 1;
                        border-style: solid;
                        border-width: 2px 0 0 2px;
                        border-radius: 8px 0 0 0;
                        margin-left: 16px;
                        margin-top: 8px;
                    }
                        
                    .chatlog__attachment-icon {
                        float: left;
                        height: 37px;
                        margin-right: 10px;
                    }
                        
                    .chatlog__header {
                        margin-bottom: 0.1rem;
                    }
                        
                    .chatlog__message-aside {
                        grid-column: 1;
                        width: 72px;
                        padding: 0.15rem 0.15rem 0 0.15rem;
                        text-align: center;
                    }
                        
                    .chatlog__message:hover {
                        background-color: #32353b;
                    }
                        
                    .chatlog__message:hover .chatlog__short-timestamp {
                        display: block;
                    }
                        
                    .chatlog__short-timestamp {
                        display: none;
                        color: #a3a6aa;
                        font-size: 0.75rem;
                        font-weight: 500;
                        direction: ltr;
                        unicode-bidi: bidi-override;
                    }
                        
                    .chatlog__message-primary {
                        grid-column: 2;
                        min-width: 0;
                    }
                        
                    .chatlog__followup {
                        display: flex;
                        margin-bottom: 0.15rem;
                        align-items: center;
                        color: #b5b6b8;
                        font-size: 0.875rem;
                        white-space: nowrap;
                        overflow: hidden;
                        text-overflow: ellipsis;
                    }
                        
                    .chatlog__followup-avatar {
                        width: 16px;
                        height: 16px;
                        margin-right: 0.25rem;
                        border-radius: 50%;
                    }
                        
                    .chatlog__followup-author {
                        user-select: none;
                        cursor: pointer;
                        margin-right: 0.3rem;
                        font-weight: 600;
                    }
                        
                    .chatlog__followup-author:hover {
                        text-decoration: underline;
                    }
                        
                    .chatlog__followup-content {
                        user-select: none;
                        overflow: hidden;
                        text-overflow: ellipsis;
                    }
                        
                    .chatlog__reference-link {
                        cursor: pointer;
                    }
                        
                    .chatlog__reference-link * {
                        display: inline;
                        pointer-events: none;
                    }
                        
                    .chatlog__reference-link .hljs {
                        display: inline;
                    }
                        
                    .chatlog__reference-link:hover {
                        color: #ffffff;
                    }
                        
                    .chatlog__reference-link:hover *:not(.chatlog__markdown-spoiler) {
                        color: inherit;
                    }
                        
                    .chatlog__interaction-link * {
                        display: inline;
                        pointer-events: none;
                    }
                        
                    .chatlog__interaction-link .hljs {
                        display: inline;
                    }
                        
                    .chatlog__interaction-link:hover *:not(.chatlog__markdown-spoiler) {
                        color: inherit;
                    }
                   \s
                    .chatlog__reference-edited-timestamp {
                        margin-left: 0.25rem;
                        color: #9599a2;
                        font-size: 0.625rem;
                        font-weight: 400;
                        line-height: 1;
                        direction: ltr;
                        unicode-bidi: bidi-override;
                        user-select: none;
                    }
                        
                    .chatlog__reference-attachment-icon {
                        flex-grow: 500;
                    }
                        
                    .chatlog__pin-avatar-container {
                        grid-column: 1;
                        width: 40px;
                        height: 15px;
                    }
                        
                    .chatlog__pin-avatar {
                        border-radius: 50%;
                        width: 25px;
                    }
                        
                    .chatlog__thread-name {
                        color: white;
                        font-weight: 600;
                    }
                        
                    .chatlog__author-name {
                        font-weight: 500;
                        color: #ffffff;
                    }
                        
                    .chatlog__author-name:hover {
                        text-decoration: underline;
                        cursor: pointer;
                    }
                        
                    .chatlog__reference-name {
                        font-weight: 500;
                        color: #ffffff;
                    }
                        
                    .chatlog__reference-name:hover {
                        text-decoration: underline;
                        cursor: pointer;
                    }
                        
                    .chatlog__timestamp {
                        margin-left: 0.3rem;
                        color: #9599a2;
                        font-size: 0.75rem;
                        font-weight: 500;
                        direction: ltr;
                        unicode-bidi: bidi-override;
                    }
                        
                    .chatlog__content {
                        padding-right: 1rem;
                        font-size: 0.95rem;
                        word-wrap: break-word;
                    }
                        
                    .chatlog__message {
                        display: grid;
                        grid-template-columns: auto 1fr;
                        padding: 0.15rem 0;
                        direction: ltr;
                        unicode-bidi: bidi-override;
                    }
                        
                    .chatlog__avatar {
                        width: 40px;
                        height: 40px;
                        border-radius: 50%;
                    }
                        
                    .chatlog__attachment {
                        margin-top: 0.3em;
                    }
                        
                    .chatlog__attachment-thumbnail {
                        vertical-align: top;
                        max-width: 45vw;
                        max-height: 225px;
                        border-radius: 3px;
                    }
                        
                    .chatlog__attachment-audio-container {
                        height: 80px;
                        width: 100%;
                        max-width: 520px;
                        padding: 10px;
                        border-radius: 3px;
                        overflow: hidden;
                        background-color: #2f3136;
                        border: 1px solid #292b2f;
                    }
                        
                    .chatlog__attachment-container {
                        height: 40px;
                        width: 100%;
                        max-width: 520px;
                        padding: 10px;
                        border-radius: 3px;
                        overflow: hidden;
                        background-color: #2f3136;
                        border: 1px solid #292b2f;
                    }
                        
                    .chatlog__attachment-filesize {
                        color: #72767d;
                        font-size: 12px;
                    }
                        
                    .chatlog__attachment-filename {
                        overflow: hidden;
                        white-space: nowrap;
                        text-overflow: ellipsis;
                    }
                        
                    .chatlog__embed {
                        display: flex;
                        margin-top: 0.3em;
                        max-width: 520px;
                    }
                        
                    .chatlog__embed-color-pill {
                        flex-shrink: 0;
                        width: 0.25em;
                        border-top-left-radius: 3px;
                        border-bottom-left-radius: 3px;
                    }
                        
                    .chatlog__embed-content-container {
                        display: flex;
                        flex-direction: column;
                        padding: 0.5em 0.6em;
                        border: 1px solid;
                        border-top-right-radius: 3px;
                        border-bottom-right-radius: 3px;
                    }
                        
                    .chatlog__embed-content {
                        display: flex;
                        width: 100%;
                    }
                        
                    .chatlog__embed-text {
                        flex: 1;
                    }
                        
                    .chatlog__embed-author {
                        display: flex;
                        margin-bottom: 0.3em;
                        align-items: center;
                    }
                        
                    .chatlog__embed-author-icon {
                        margin-right: 0.5em;
                        width: 20px;
                        height: 20px;
                        border-radius: 50%;
                    }
                        
                    .chatlog__embed-author-name {
                        font-size: 0.875em;
                        font-weight: 600;
                    }
                        
                    .chatlog__embed-title {
                        margin-bottom: 0.2em;
                        font-size: 0.875em;
                        font-weight: 600;
                    }
                        
                    .chatlog__embed-description {
                        font-weight: 500;
                        font-size: 0.85em;
                        color: rgba(255, 255, 255, 0.6);
                    }
                        
                    .chatlog__embed-fields {
                        display: flex;
                        flex-wrap: wrap;
                    }
                        
                    .chatlog__embed-field {
                        flex: 0;
                        min-width: 100%;
                        max-width: 506px;
                        padding-top: 0.6em;
                        font-size: 0.875em;
                    }
                        
                    .chatlog__embed-field--inline {
                        flex: 1;
                        flex-basis: auto;
                        min-width: 150px;
                    }
                        
                    .chatlog__embed-field-name {
                        margin-bottom: 0.2em;
                        font-weight: 600;
                        color: #ffffff;
                    }
                        
                    .chatlog__embed-field-value {
                        font-weight: 500;
                        color: rgba(255, 255, 255, 0.6);
                    }
                        
                    .chatlog__embed-thumbnail {
                        flex: 0;
                        margin-left: 1.2em;
                        max-width: 80px;
                        max-height: 80px;
                        border-radius: 3px;
                    }
                        
                    .chatlog__embed-image-container {
                        margin-top: 0.6em;
                    }
                        
                    .chatlog__embed-image {
                        max-width: 500px;
                        max-height: 400px;
                        border-radius: 3px;
                    }
                        
                    .chatlog__embed-footer {
                        margin-top: 0.6em;
                        color: rgba(255, 255, 255, 0.6);
                    }
                        
                    .chatlog__embed-footer-icon {
                        margin-right: 0.2em;
                        width: 20px;
                        height: 20px;
                        border-radius: 50%;
                        vertical-align: middle;
                    }
                        
                    .chatlog__embed-footer-text {
                        font-size: 0.75em;
                        font-weight: 500;
                    }
                        
                    .chatlog__components {
                        display: flex;
                    }
                        
                    .chatlog__component-button {
                        display: flex;
                        align-items: center;
                        margin: 0.35em 0.1em 0.1em 0.1em;
                        padding: 0.2em 0.35em;
                        border-radius: 2px;
                    }
                        
                    .chatlog__button-label {
                        min-width: 9px;
                        margin-left: 0.35em;
                        margin-right: 0.35em;
                        font-size: 0.875em;
                        color: white;
                        font-weight: 500;
                    }
                        
                    .chatlog__button-label img {
                        padding-right: 5px;
                    }
                        
                    .chatlog__reactions {
                        display: flex;
                    }
                        
                    .chatlog__reaction {
                        display: flex;
                        align-items: center;
                        margin: 0.35em 0.1em 0.1em 0.1em;
                        padding: 0.2em 0.35em;
                        border-radius: 8px;
                        background-color: rgba(255, 255, 255, 0.05);
                    }
                        
                    .chatlog__reaction-count {
                        min-width: 9px;
                        margin-left: 0.35em;
                        font-size: 0.875em;
                    }
                        
                    .chatlog__bot-tag {
                        background: #5865f2;
                        color: #ffffff;
                        padding: 0 0.2rem;
                        margin-top: 0.5em;
                        border-radius: 0.1875rem;
                        margin-left: 0.07rem;
                        position: relative;
                        vertical-align: top;
                        display: inline-flex;
                        flex-shrink: 0;
                        text-indent: 0;
                        font-weight: 500;
                        font-size: 10px;
                        line-height: 15px;
                    }
                        
                    .chatlog__bot-tag-verified {
                        width: 16px;
                        margin-left: -3px;
                    }
                        
                    .chatlog__followup .chatlog__bot-tag {
                        margin-right: 0.3rem;
                        margin-top: 0;
                    }
                        
                    .markup {
                        white-space: normal;
                    }
                        
                    .markup ul {
                        margin: 4px 0 0 16px;
                        margin-block-start: 0;
                        margin-block-end: 0;
                        padding-inline-start: 0;
                        list-style-position: outside;
                        list-style-type: circle;
                    }
                        
                    .markup ul ul {
                        list-style-type: circle;
                        margin-bottom: 0;
                    }
                        
                    .markup li {
                        margin: 0;
                        padding: 0;
                        border: 0;
                        font-weight: inherit;
                        font-style: inherit;
                        font-family: inherit;
                        font-size: 100%;
                        vertical-align: baseline;
                    }
                        
                        
                    .meta__details .chatlog__bot-tag {
                        margin-left: 1ch;
                    }
                        
                    /* Postamble */
                        
                    .postamble {
                        margin: 1.4em 0.3em 0.6em 0.3em;
                        padding: 1em;
                        border-top: 1px solid;
                    }
                        
                    /* General */
                        
                    html, body {
                        height: 100%;
                        width: 100%;
                        background-color: #36393f;
                        font-family: "gg sans", Helvetica, Arial, sans-serif;
                        font-size: 17px;
                        color: #fff;
                        margin: 0;
                        padding: 0;
                    }
                        
                    a {
                        color: #0096cf;
                    }
                        
                    .spoiler-text {
                        background-color: rgba(255, 255, 255, 0.1);
                    }
                        
                    .spoiler--hidden .spoiler-text {
                        background-color: #202225;
                    }
                        
                    .spoiler--hidden:hover .spoiler-text {
                        background-color: rgba(32, 34, 37, 0.8);
                    }
                        
                    .quote {
                        border-color: #4f545c;
                    }
                        
                    .pre {
                        background-color: #2f3136 !important;
                    }
                        
                    .pre--multiline {
                        border-color: #282b30 !important;
                        color: #b9bbbe !important;
                    }
                        
                    /* Chatlog */
                        
                    .chatlog__message-group {
                        margin-bottom: 1rem;
                        border-color: rgba(255, 255, 255, 0.1);
                    }
                        
                    .chatlog__message-container {
                        background-color: transparent;
                        transition: background-color 1s ease;
                    }
                        
                    .chatlog__message-container--highlighted {
                        background-color: rgba(114, 137, 218, 0.2);
                    }
                        
                    .chatlog__followup-symbol {
                        height: 10px;
                        margin: 6px 4px 4px 36px;
                        border-left: 2px solid #4f545c;
                        border-top: 2px solid #4f545c;
                        border-radius: 8px 0 0 0;
                        border-color: #4f545c;
                    }
                        
                    .chatlog__reference-icon {
                        width: 20px;
                        display: inline-block;
                        vertical-align: bottom;
                    }
                        
                    .chatlog__interaction-icon {
                        width: 15px;
                        display: inline-block;
                        vertical-align: bottom;
                        margin-left: 3px;
                        margin-bottom: 2px;
                    }
                        
                    .chatlog__reference-link {
                        color: #b5b6b8;
                    }
                        
                    .chatlog__interaction-filler {
                        color: #b5b6b8;
                    }
                        
                    .chatlog__interaction-link {
                        color: #1680ab;
                        cursor: pointer;
                    }
                        
                    .chatlog__pinned-link {
                        color: white;
                        font-weight: 600;
                    }
                        
                    .chatlog__reference-link:hover {
                        color: #ffffff;
                    }
                        
                    .chatlog__interaction-link:hover {
                        text-decoration: underline;
                    }
                        
                    .chatlog__message--highlighted {
                        background-color: rgba(114, 137, 218, 0.2) !important;
                    }
                        
                    .chatlog__message--pinned {
                        background-color: rgba(249, 168, 37, 0.05);
                    }
                        
                    .chatlog__embed-color-pill--default {
                        background-color: rgba(79, 84, 92, 1);
                    }
                        
                    .chatlog__embed-content-container {
                        background-color: rgba(46, 48, 54, 0.3);
                        border-color: rgba(46, 48, 54, 0.6);
                    }
                        
                    .chatlog__embed-author-name {
                        color: #ffffff;
                    }
                        
                    .chatlog__embed-author-name-link {
                        color: #ffffff;
                    }
                        
                    .chatlog__embed-title {
                        color: #ffffff;
                    }
                        
                    .chatlog__reaction-count {
                        color: rgba(255, 255, 255, 0.3);
                    }
                        
                    /* === INFO === */
                        
                    .panel {
                        display: flex;
                        flex-shrink: 0;
                        align-items: center;
                        padding: 6px 0 6px 0;
                        user-select: none;
                        font-weight: 700;
                        font-size: 20px;
                        box-shadow: 0 1px 0 rgba(4, 4, 5, 0.2), 0 1.5px 0 rgba(6, 6, 7, 0.05), 0 2px 0 rgba(4, 4, 5, 0.05);
                    }
                        
                    .panel__hashtag-icon {
                        width: 24px;
                        height: 24px;
                        margin-left: 16px;
                        margin-right: 8px;
                        margin-top: 1px;
                    }
                        
                    .panel__channel-topic {
                        border-left-style: solid;
                        border-color: #4f545c;
                        border-width: 1px;
                        color: #b9bbbe;
                        margin-left: 10px;
                        padding-left: 10px;
                        font-size: 14px;
                        line-height: 18px;
                        height: 18px;
                        white-space: nowrap;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        font-weight: 500;
                    }
                        
                    .panel__summary-button {
                        display: flex;
                        align-items: center;
                        padding: 0.2em 0.35em;
                        border-radius: 2px;
                        margin-left: auto;
                        margin-right: 50px;
                        color: #b9bbbe;
                    }
                        
                    .panel__summary-button:hover {
                        color: #fff;
                        cursor: pointer;
                    }
                        
                    .summary-popout.visible {
                        transform: scale(1);
                        transition: transform 200ms ease-in-out;
                    }
                        
                    .summary-popout {
                        position: absolute;
                        z-index: 6969;
                        background-color: #36393f;
                        box-shadow:
                            0 2px 10px 0 rgb(0 0 0 / 20%),
                            0 0 0 1px rgb(32 34 37 / 60%);
                        width: 250px;
                        border-radius: 5px;
                        overflow: hidden;
                        transform: scale(0);
                        transform-origin: top right;
                    }
                        
                    .main {
                        display: flex;
                        overflow-y: auto;
                        height: calc(100% - 126px);
                        flex-direction: column;
                    }
                        
                    .main::-webkit-scrollbar {
                      width: 8px;
                      background-color: rgba(0,0,0,0);
                    }
                        
                    /* Add a thumb */
                    .main::-webkit-scrollbar-thumb {
                      background-color: #202225;
                      border: 0 #36393f solid;
                      border-left-width: 1px;
                      border-right-width: 1px;
                    }
                        
                    .buffer {
                        flex: 1;
                    }
                        
                    .info {
                        margin: 0 16px;
                        padding-bottom: 12px;
                        display: flex;
                        flex-direction: column;
                        justify-content: flex-end;
                        flex-shrink: 0;
                        user-select: none;
                    }
                        
                    .info__title {
                        font-size: 32px;
                        font-weight: 700;
                        line-height: 40px;
                    }
                        
                    .info__subject {
                        color: #b9bbbe;
                        font-size: 16px;
                        line-height: 20px;
                    }
                        
                    .info__channel-message-count {
                        margin-top: 2px;
                    }
                        
                    .footer {
                        flex-shrink: 0;
                        display: flex;
                        align-items: center;
                        height: 20px;
                        border-radius: 5px;
                        margin: 0 16px 24px;
                        padding: 16px;
                        width: calc(100% - 50px);
                        background-color: #202225;
                        position: relative;
                        z-index: 10;
                        user-select: none;
                    }
                        
                    .footer__text {
                        font-size: 16px;
                        line-height: 20px;
                        font-weight: 400;
                        white-space: nowrap;
                        overflow: hidden;
                        text-overflow: ellipsis;
                    }
                        
                    #context-menu {
                        position: absolute;
                        width: 188px;
                        border-radius: 4px;
                        padding: 6px 8px;
                        background-color: #18191c;
                        box-shadow: 0 8px 16px rgba(0, 0, 0, .24);
                        font-weight: 500;
                        font-size: 14px;
                        line-height: 18px;
                        color: #b9bbbe;
                        transform: scale(0);
                        transform-origin: top left;
                        z-index: 6969;
                    }
                        
                    #context-menu.visible {
                        transform: scale(1);
                        transition: transform 200ms ease-in-out;
                    }
                        
                    #context-menu .item {
                        margin: 2px 0;
                        padding: 0 8px;
                        display: flex;
                        align-items: center;
                        border-radius: 2px;
                        min-height: 32px;
                        cursor: pointer;
                    }
                        
                    #context-menu .item:hover {
                        color: #fff;
                    }
                        
                    .meta-popout {
                        position: absolute;
                        z-index: 6969;
                        background-color: #292b2f;
                        box-shadow:
                            0 2px 10px 0 rgb(0 0 0 / 20%),
                            0 0 0 1px rgb(32 34 37 / 60%);
                        width: 280px;
                        border-radius: 5px;
                        overflow: hidden;
                        transform: scale(0);
                        transform-origin: top left;
                    }
                        
                    .meta-popout img {
                        height: 16px;
                        width: 16px;
                    }
                        
                    .meta__img-border {
                        border-radius: 50%;
                    }
                        
                    .meta-popout.mounted {
                        transform: scale(1);
                        transition: transform .3 ease-in-out;;
                    }
                        
                    .meta__divider {
                        height: 4px;
                        width: 4px;
                        border-radius: 50%;
                        background-color: #4f545c;
                    }
                        
                    .meta__divider-2 {
                        margin: 5px 12px 10px;
                        height: 1px;
                        background-color: #4f545c;
                    }
                        
                    .meta__header {
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        justify-content: center;
                        background-color: #202225;
                        padding-top: 10px;
                    }
                        
                    .meta__header img {
                        user-select: none;
                        border-radius: 50%;
                        margin-bottom: 10px;
                        position: relative;
                        width: 80px;
                        height: 80px;
                    }
                        
                    .meta__details {
                        display: flex;
                        flex-wrap: wrap;
                        padding-bottom: 7px;
                    }
                        
                    .meta__display-name {
                        font-weight: 500;
                        color: #fff;
                        opacity: 0.9;
                        text-overflow: ellipsis;
                        overflow: hidden;
                        font-size: 12px;
                    }
                        
                    .meta__user {
                        font-weight: 500;
                        color: #fff;
                        text-overflow: ellipsis;
                        overflow: hidden;
                    }
                        
                    .meta__discriminator {
                        font-weight: 500;
                        color: #fff;
                        opacity: .6;
                    }
                        
                    .meta-popout .meta__header .bot {
                        margin-left: 1ch;
                    }
                        
                    .meta__description {
                        padding: 10px;
                        background-color: #18191c;
                        margin: 10px;
                        border-radius: 5%;
                    }
                        
                    .meta__field {
                        margin-bottom: 10px;
                    }
                        
                    .meta__title {
                        font-weight: 700;
                        color: #72767d;
                        text-transform: uppercase;
                        font-size: 12px;
                        line-height: 16px;
                        margin-bottom: 1px;
                    }
                        
                    .meta__value {
                        font-size: 14px;
                        line-height: 16px;
                        align-items: center;
                        display: flex;
                        column-gap: 6px;
                    }
                        
                    .meta__support {
                        text-align: right;
                    }
                        
                    .meta__support a {
                        color: #72767d;
                        text-transform: uppercase;
                        font-size: 10px;
                        font-weight: 700;
                    }
                        
                    .dropdownButton {
                      width: 100%;
                      color: #6E767D;
                      padding: 11.5px;
                      font-size: 15px;
                      cursor: pointer;
                      text-align: left;
                      border-radius: 5px;
                      background-color: #2F3136;
                      border: 1px solid #202225;
                    }
                        
                    .chatlog__component-disabled {
                      cursor: not-allowed;
                      opacity: 0.6;
                    }
                        
                    .chatlog__component-disabled a {
                      cursor: not-allowed;
                    }
                        
                    .chatlog__component-disabled button {
                      cursor: not-allowed;
                    }
                        
                    .chatlog__component-dropdown {
                      width: 400px;
                      margin-top: 5px;
                      position: relative;
                      display: inline-block;
                    }
                        
                    .chatlog__dropdown-content {
                      position: relative;
                    }
                        
                    .chatlog__dropdown-emoji {
                      left: 10px;
                      position: absolute;
                    }
                        
                    .chatlog__dropdown-emoji img {
                      width: 1.4em;
                      height: 1.4em;
                    }
                        
                    .chatlog__dropdown-text {
                      padding-left: 25px
                    }
                        
                    .chatlog__role-icon {
                        height: 16px;
                        width: 16px;
                        margin-left: 5px;
                        transform: translateY(2px);
                    }
                        
                    .dropdownContent {
                      z-index: 1;
                      display: none;
                      width: 99.5%;
                      font-size: 14px;
                      position: absolute;
                      margin-top: -0.7px;
                      background-color: #2F3136;
                      border: 1px solid #202225;
                      box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                    }
                        
                    .dropdownContentEmoji {
                      width: 25px;
                      position: absolute;
                    }
                        
                    .dropdownContentTitle {
                      color: white;
                      font-weight: 600;
                    }
                        
                    .dropdownContent a {
                      display: block;
                      padding: 12px 16px;
                      text-decoration: none;
                    }
                        
                    .chatlog__dropdown-icon {
                      right: 0px;
                      width: 25px;
                      margin-top: -12px;
                      position: absolute;
                      padding-right: 4px;
                    }
                        
                    .chatlog__component-dropdown-border {
                      border-bottom-left-radius: 0px;
                      border-bottom-right-radius: 0px;
                    }
                        
                    .tippy-box[data-theme~='disc'] {
                        font-weight: 600;
                        background-color: #111214;
                        color: #dfe0e4;
                    }
                        
                    .tippy-box[data-theme~='disc'][data-placement^='top'] > .tippy-arrow::before {
                      border-top-color: #111214;
                    }
                        
                    .tippy-box[data-theme~='disc'][data-placement^='left'] > .tippy-arrow::before {
                      border-left-color: #111214;
                    }
                        
                    .tippy-box[data-theme~='disc'][data-placement^='bottom'] > .tippy-arrow::before {
                      border-bottom-color: #111214;
                    }
                        
                    .tippy-box[data-theme~='disc'][data-placement^='right'] > .tippy-arrow::before {
                      border-right-color: #111214;
                    }
                        
                    .dropdownContentDesc { color: #999B9E; }
                    .dropdownButton:hover { border: 1px solid #040405; }
                    .chatlog__component-dropdown-show { display: block; }
                    .chatlog__component-dropdown a:hover { background-color: #292B2F; }
                        
                    .cursor_pointer { cursor: pointer; }
                        
                </style>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.6/styles/solarized-dark.min.css">
                <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.6/highlight.min.js"></script>
                <script src="https://unpkg.com/@popperjs/core@2.11.5/dist/umd/popper.min.js"></script>
                <script src="https://unpkg.com/tippy.js@6.3.7/dist/tippy-bundle.umd.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/dayjs@1/dayjs.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/dayjs@1/plugin/timezone.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/dayjs@1/plugin/utc.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/dayjs@1.11.5/plugin/customParseFormat.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/dayjs@1.11.5/plugin/isToday.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/dayjs@1.11.5/plugin/isTomorrow.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/dayjs@1.11.5/plugin/isBetween.js"></script>
                        
                <script>
                    <!--  Scroll to Message (References)  -->
                    function scrollToMessage(event, id) {
                        var element = document.getElementById('message-' + id);
                        
                        if (element !== null && element !== undefined) {
                            event.preventDefault();
                        
                            element.classList.add('chatlog__message-container--highlighted');
                        
                            window.scrollTo({
                                top: element.getBoundingClientRect().top - document.body.getBoundingClientRect().top - (window.innerHeight / 2),
                                behavior: 'smooth'
                            });
                        
                            window.setTimeout(function() {
                                element.classList.remove('chatlog__message-container--highlighted');
                            }, 2000);
                        }
                    }
                        
                    function scrollToMessage(event, id) {
                        var element = document.getElementById('message-' + id);
                        
                        if (element) {
                            event.preventDefault();
                        
                            element.classList.add('chatlog__message--highlighted');
                        
                            window.scrollTo({
                                top: element.getBoundingClientRect().top - document.body.getBoundingClientRect().top - (window.innerHeight / 2),
                                behavior: 'smooth'
                            });
                        
                            window.setTimeout(function() {
                                element.classList.remove('chatlog__message--highlighted');
                            }, 2000);
                        }
                    }
                        
                    <!--  Spoiler (|| Spoiler ||)  -->
                    function showSpoiler(event, element) {
                        if (element && element.classList.contains('spoiler--hidden')) {
                            event.preventDefault();
                            element.classList.remove('spoiler--hidden');
                        }
                    }
                        
                    <!--  Menu Dropdown (Selectmenu)  -->
                    function showDropdown(dropdownID) {
                      <!--    If you are reading this and can improve the JS logic, let me know!-->
                      document.getElementById("dropdownButton" + dropdownID).classList.toggle("chatlog__component-dropdown-border");
                      document.getElementById("dropdownMenuContent" + dropdownID).classList.toggle("chatlog__component-dropdown-show");
                      document.getElementById("dropdownMenu" + dropdownID).classList.toggle("chatlog__component-dropdown-show");
                    }
                        
                    <!--  Code Block Markdown (```lang```) -->
                    document.addEventListener('DOMContentLoaded', () => {
                        document.querySelectorAll('.pre--multiline').forEach((block) => {
                            hljs.highlightBlock(block);
                        });
                    });
                </script>
            </head>
            <body>
                        
            <div class="panel">
                <img class="panel__hashtag-icon" src="https://cdn.jsdelivr.net/gh/mahtoid/DiscordUtils@master/discord-hashtag.svg"/>
                <span>{{CHANNEL_NAME}}</span>
                        
                {{CHANNEL_TOPIC}}
                        
                <div class="panel__summary-button" id="summary-button">
                    <span>Summary</span>
                </div>
            </div>
                        
            <div class="main">
                <div class="buffer">
                    <!-- Magical space goes here -->
                </div>
                        
                <div class="info">
                    <span class="info__title">Welcome to #{{CHANNEL_NAME}}!</span>
                    {{SUBJECT}}
                </div>
                        
                <div class="chatlog">
                    {{MESSAGES}}
                </div>
            </div>
                        
            <div class="footer">
                <span class="footer__text">This transcript was generated on {{DATE_TIME}}</span>
            </div>
                        
            <div id="context-menu">
                <div class="item">Copy Message ID</div>
            </div>
                        
            <div id="summary-popout" class="summary-popout">
                <div class="meta__header">
                     <img src="{{SERVER_AVATAR_URL}}" alt="Avatar">
                </div>
                <div class="meta__description">
                    <div class="meta__details">
                        <div class="meta__user">{{SERVER_NAME}}</div>
                    </div>
                    <div class="meta__field">
                        <div class="meta__title">Guild ID</div>
                        <div class="meta__value">{{GUILD_ID}}</div>
                    </div>
                    <div class="meta__field">
                        <div class="meta__title">Channel ID</div>
                        <div class="meta__value">{{CHANNEL_ID}}</div>
                    </div>
                    <div class="meta__field">
                        <div class="meta__title">Channel Creation Date</div>
                        <div class="meta__value">{{CHANNEL_CREATED_AT}}</div>
                    </div>
                    <div class="meta__field">
                        <div class="meta__title">Total Message Count</div>
                        <div class="meta__value">{{MESSAGE_COUNT}}</div>
                    </div>
                    <div class="meta__field">
                        <div class="meta__title">Total Message Participants</div>
                        <div class="meta__value">{{MESSAGE_PARTICIPANTS}}</div>
                    </div>
                    {{SD}}
                </div>
            </div>
            {{META_DATA}}
                        
            <script>
                <!-- Right Click - Copy ID -->
                let metaPopout = undefined
                const contextMenu = document.getElementById("context-menu");
                const scope = document.querySelector("body");
                const messages = document.getElementsByClassName("chatlog__message-container")
                let messageID = ""
                const normalisePosition = (mouseX, mouseY, type) => {
                    if (type == "context") {
                        maxWidth = contextMenu.clientWidth
                        maxHeight = contextMenu.clientHeight
                    } else if (type == "user") {
                        maxWidth = metaPopout.clientWidth
                        maxHeight = metaPopout.clientHeight
                    }
                        
                    let {
                        left: scopeOffsetX,
                        top: scopeOffsetY,
                    } = scope.getBoundingClientRect()
                    scopeOffsetX = scopeOffsetX < 0 ? 0 : scopeOffsetX;
                    scopeOffsetY = scopeOffsetY < 0 ? 0 : scopeOffsetY
                    const scopeX = mouseX - scopeOffsetX;
                    const scopeY = mouseY - scopeOffsetY
                        
                    const outOfBoundsOnX = scopeX + maxWidth > scope.clientWidth
                    const outOfBoundsOnY = scopeY + maxHeight > scope.clientHeight
                    let normalizedX = mouseX;
                    let normalizedY = mouseY
                        
                    if (outOfBoundsOnX) {
                        normalizedX = scopeOffsetX + scope.clientWidth - maxWidth;
                    }
                        
                    if (outOfBoundsOnY) {
                        normalizedY = scopeOffsetY + scope.clientHeight - maxHeight;
                    }
                        
                    return { normalizedX, normalizedY };
                }
                        
                scope.addEventListener("contextmenu", (e) => {
                    event.preventDefault()
                    if (e.target.offsetParent != contextMenu) {
                      contextMenu.classList.remove("visible");
                    }
                })
                        
                var openContextMenu = function() {
                    const { clientX: mouseX, clientY: mouseY } = event
                    const { normalizedX, normalizedY } = normalisePosition(mouseX, mouseY, "context")
                        
                    contextMenu.classList.remove("visible")
                    contextMenu.style.left = `${normalizedX}px`;
                    contextMenu.style.top = `${normalizedY}px`
                        
                    setTimeout(() => {
                        contextMenu.classList.add("visible");
                    })
                    messageID = this.getAttribute("data-message-id");
                }
                        
                for (var i = 0; i < messages.length; i++) {
                    messages[i].addEventListener("contextmenu", openContextMenu)
                }
                        
                scope.addEventListener("click", (e) => {
                    if (e.target.offsetParent != contextMenu) {
                      contextMenu.classList.remove("visible");
                    } else {
                        navigator.clipboard.writeText(messageID)
                        contextMenu.classList.remove("visible");
                    }
                        
                    if (metaPopout && e.target.offsetParent != metaPopout) {
                        metaPopout.classList.remove("mounted")
                    }
                        
                    if (e.target.offsetParent != summaryPopout) {
                        summaryPopout.classList.remove("visible")
                    }
                })
                        
                mainScroll = document.querySelector('.main')
                mainScroll.addEventListener('scroll', (e) => {
                    if (e.target.offsetParent != contextMenu) {
                        contextMenu.classList.remove("visible");
                    } else {
                        navigator.clipboard.writeText(messageID)
                        contextMenu.classList.remove("visible");
                    }
                        
                    if (metaPopout && e.target.offsetParent != metaPopout) {
                        metaPopout.classList.remove("mounted")
                    }
                });
                        
                <!-- User Dialog -->
                const summaryPopout = document.getElementById('summary-popout')
                        
                window.onload = function() {
                    var author_name = document.getElementsByClassName('chatlog__author-name');
                    var followup_author =document.getElementsByClassName('chatlog__followup-author');
                    var followup_avatar = document.getElementsByClassName("chatlog__followup-avatar");
                    var avatar = document.getElementsByClassName("chatlog__avatar");
                    const element_select = [...author_name, ...followup_author, ...followup_avatar, ...avatar]
                        
                    for(var i = 0; i < element_select.length; i++) {
                        var element = element_select[i];
                        element.onclick = function() {
                            authorID = this.getAttribute("data-user-id");
                        
                            if (metaPopout) {
                                metaPopout.classList.remove('mounted');
                            }
                        
                            metaPopout = document.getElementById('meta-popout-' + authorID);
                        
                            const subtractX = document.querySelector('.main').scrollLeft
                            const subtractY = document.querySelector('.main').scrollTop
                        
                            const elementX = this.offsetLeft + this.offsetWidth + 10 - subtractX
                            const elementY = this.offsetTop - subtractY
                        
                            const { normalizedX, normalizedY } = normalisePosition(elementX, elementY, "user")
                        
                            metaPopout.style.left = `${normalizedX}px`;
                            metaPopout.style.top = `${normalizedY}px`;
                        
                            setTimeout(() => {
                                metaPopout.classList.add("mounted")
                            });
                        }
                    }
                        
                    var summaryButton = document.getElementById('summary-button');
                    summaryButton.onclick = function() {
                        const elementX = this.offsetLeft - 110
                        const elementY = this.offsetTop + 30
                        
                        summaryPopout.style.left = `${elementX}px`;
                        summaryPopout.style.top = `${elementY}px`;
                        
                        setTimeout(() => {
                            summaryPopout.classList.add("visible")
                        });
                    }
                }
                        
                <!-- Timestamps: Tooltips -->
                tippy('.chatlog__timestamp', {
                    placement: 'top',
                    animation: 'fade',
                    content: (reference) => reference.getAttribute('data-timestamp'),
                    theme: 'disc',
                });
                        
                tippy('.chatlog__short-timestamp', {
                    placement: 'top',
                    animation: 'fade',
                    content: (reference) => reference.getAttribute('data-timestamp'),
                    theme: 'disc',
                });
                        
                tippy('.chatlog__reference-edited-timestamp', {
                    placement: 'top',
                    animation: 'fade',
                    content: (reference) => reference.getAttribute('data-timestamp'),
                    theme: 'disc',
                });
                        
                <!-- Timestamps: Tooltips -->
                tippy('.unix-timestamp', {
                    placement: 'top',
                    animation: 'fade',
                    content: (reference) => reference.getAttribute('data-timestamp'),
                    theme: 'disc',
                });
                        
                {{FANCY_TIME}}
            </script>
                        
            </body>
            </html>
            """;


    public static final class Script {
        private Script() {}

        public static final String CHANNEL_SUBJECT = "<span class=\"info__subject\">This is the {{LIMIT}} of the #{{CHANNEL_NAME}} channel. {{RAW_CHANNEL_TOPIC}}</span>";

        public static final String CHANNEL_TOPIC = "<span class=\"panel__channel-topic\">{{CHANNEL_TOPIC}}</span>";

        public static final String FANCY_TIME = """
                <!-- Timestamps: Content -->
                dayjs.extend(window.dayjs_plugin_utc);
                dayjs.extend(window.dayjs_plugin_timezone);
                dayjs.extend(window.dayjs_plugin_customParseFormat);
                dayjs.extend(window.dayjs_plugin_isToday);
                dayjs.extend(window.dayjs_plugin_isTomorrow);
                dayjs.extend(window.dayjs_plugin_isBetween);
                                
                dayjs.tz.setDefault("{{TIMEZONE}}")
                dayjs().format("DD/MM/YYYY HH:mm");
                var timeStamps = document.getElementsByClassName('chatlog__timestamp');
                for(var i = 0; i < timeStamps.length; i++) {
                    const date_1 = dayjs.tz(timeStamps[i].innerText, "DD-MM-YYYY HH:mm", "{{TIMEZONE}}");
                    const date_2 = dayjs.tz();
                    const diff = date_2.diff(date_1, 'day', true)
                                
                    if (date_1.isTomorrow()) {
                        timeStamps[i].ineerText = "Tomorrow at " + date_1.format('HH:mm')
                    } else if (date_1.isToday()) {
                        timeStamps[i].innerText = "Today at " + date_1.format('HH:mm')
                    } else if (date_1.add(1, 'day').isToday()) {
                        timeStamps[i].innerText = "Yesterday at " + date_1.format('HH:mm')
                    } else if (date_1.isBetween(date_2, date_2.subtract(7, 'day'))) {
                        timeStamps[i].innerText = date_1.day(date_1.day()).format("dddd [at] HH:mm")
                    }
                }
                """;
    }

    public static final class Reaction {

        private Reaction() {}

        public static final String CUSTOM_EMOJI = """
                <div class=chatlog__reaction>
                    <img class="emoji emoji--small" src="https://cdn.discordapp.com/emojis/{{EMOJI}}.{{EMOJI_FILE}}">
                    <span class="chatlog__reaction-count">{{EMOJI_COUNT}}</span>
                </div>
                """;

        public static final String EMOJI = """
                <div class=chatlog__reaction>
                    {{EMOJI}}
                    <span class="chatlog__reaction-count">{{EMOJI_COUNT}}</span>
                </div>
                """;
    }

    public static final class Message {

        private Message() {}

        public static final String BOT_TAG_VERIFIED = """
                <div class=chatlog__reaction>
                    {{EMOJI}}
                    <span class="chatlog__reaction-count">{{EMOJI_COUNT}}</span>
                </div>
                """;

        public static final String BOT_TAG = "<span class=\"chatlog__bot-tag\">BOT</span>";

        public static final String CONTENT = """
                <span class="chatlog__markdown-preserve">{{MESSAGE_CONTENT}}</span>
                {{EDIT}}
                """;

        public static final String END = "</div>";

        public static final String INTERACTION = """
                <div class="chatlog__followup">
                        <img class="chatlog__followup-avatar" src="{{AVATAR_URL}}" alt="Avatar" loading="lazy" data-user-id="{{USER_ID}}">
                        {{BOT_TAG}}
                        <div class="chatlog__followup-author" style="{{USER_COLOUR}}" data-user-id="{{USER_ID}}">{{NAME}}</div>
                        <div class="chatlog__followup-content">
                            <span class="chatlog__interaction-filler">{{FILLER}}</span>
                            <span class="chatlog__interaction-link">{{COMMAND}}</span>
                        </div>
                </div>
                """;

        public static final String MESSAGE = """
                            <div id="chatlog__message-container-{{MESSAGE_ID}}" class="chatlog__message-container" data-message-id="{{MESSAGE_ID}}">
                                    <div class="chatlog__message">
                                
                                        <div class="chatlog__message-aside">
                                            <div class="chatlog__short-timestamp" data-timestamp="{{TIMESTAMP}}">{{TIME}}</div>
                                        </div>
                                
                                        <div class="chatlog__message-primary">
                                            <div class="chatlog__content chatlog__markdown" data-message-id="{{MESSAGE_ID}}" id="message-{{MESSAGE_ID}}">
                                                {{MESSAGE_CONTENT}}
                                
                                                {{ATTACHMENTS}}
                                
                                                {{EMBEDS}}
                                
                                                {{COMPONENTS}}
                                
                                                {{EMOJI}}
                                
                                            </div>
                                        </div>
                                    </div>
                            </div>
                """;

        public static final String META = """
                
                <div id="meta-popout-{{USER_ID}}" class="meta-popout">
                    <div class="meta__header">
                         <img src="{{USER_AVATAR}}" alt="Avatar">
                    </div>
                    <div class="meta__description">
                        {{DISPLAY}}
                        <div class="meta__details">
                            <div class="meta__user">{{USERNAME}}</div>
                            <div class="meta__discriminator">{{DISCRIMINATOR}}</div>
                            {{BOT}}
                        </div>
                        <div class="meta__divider-2"></div>
                        <div class="meta__field">
                            <div class="meta__title">Member Since</div>
                            <div class="meta__value"><img src="{{DISCORD_ICON}}"/> {{CREATED_AT}} <div class="meta__divider"></div> <img src="{{GUILD_ICON}}" class="meta__img-border"/> {{JOINED_AT}}</div>
                        </div>
                        <div class="meta__field">
                            <div class="meta__title">Member ID</div>
                            <div class="meta__value">{{MEMBER_ID}}</div>
                        </div>
                        <div class="meta__field">
                            <div class="meta__title">Message Count</div>
                            <div class="meta__value">{{MESSAGE_COUNT}}</div>
                        </div>
                    </div>
                </div>
                """;

        public static final String PIN = """
                <div class="chatlog__message-group">
                    <div id="chatlog__message-container-{{MESSAGE_ID}}" class="chatlog__message-container" data-message-id="{{MESSAGE_ID}}">
                        <div class="chatlog__message">
                            <div class="chatlog__message-aside">
                               <img class="chatlog__pin-avatar" src="{{PIN_URL}}" />
                            </div>
                            <div class="chatlog__message-primary">
                                <div class="chatlog__header">
                                </div>
                                
                                <div class="chatlog__content chatlog__markdown" data-message-id="{{MESSAGE_ID}}" id="message-{{MESSAGE_ID}}">
                                    <span class="chatlog__reference-name" style="{{USER_COLOUR}}">{{NAME}}</span> has pinned
                                    <a class="chatlog__pinned-link" href="#" onclick="scrollToMessage(event, '{{REF_MESSAGE_ID}}')">
                                        a message
                                    </a>
                                    to this channel
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                """;

        public static final String REFERENCE = """
                <div class="chatlog__followup">
                        <img class="chatlog__followup-avatar" src="{{AVATAR_URL}}" alt="Avatar" loading="lazy" data-user-id="{{USER_ID}}">
                        {{BOT_TAG}}
                        <div class="chatlog__followup-author" style="{{USER_COLOUR}}" data-user-id="{{USER_ID}}">{{NAME}}</div>
                        <div class="chatlog__followup-content">
                            <span class="chatlog__reference-link" onclick="scrollToMessage(event, '{{MESSAGE_ID}}')">
                                    {{CONTENT}} {{ICON}}
                            </span>
                            {{EDIT}}
                        </div>
                </div>
                """;

        public static final String REFERENCE_UNKNOWN = """
                <div class="chatlog__followup">
                    <span class="chatlog__reference-unknown"><em>Original message was deleted.</em></span>
                </div>
                """;

        public static final String START = """
                <div class="chatlog__message-group">
                    <div id="chatlog__message-container-{{MESSAGE_ID}}" class="chatlog__message-container" data-message-id="{{MESSAGE_ID}}">
                        <div class="chatlog__message">
                            <div class="chatlog__message-aside">
                                {{REFERENCE_SYMBOL}}
                                <img class="chatlog__avatar" src="{{AVATAR_URL}}"  data-user-id="{{USER_ID}}" />
                            </div>
                            <div class="chatlog__message-primary">
                                {{REFERENCE}}
                                <div class="chatlog__header">
                                    <span class="chatlog__author-name" title="{{NAME_TAG}}" data-user-id="{{USER_ID}}" style="{{USER_COLOUR}}">{{NAME}}{{USER_ICON}}</span>
                                    {{BOT_TAG}}
                                    <span class="chatlog__timestamp" data-timestamp="{{TIMESTAMP}}">{{DEFAULT_TIMESTAMP}}</span>
                                </div>
                                
                                <div class="chatlog__content chatlog__markdown" data-message-id="{{MESSAGE_ID}}" id="message-{{MESSAGE_ID}}">
                                    {{MESSAGE_CONTENT}}
                                
                                    {{ATTACHMENTS}}
                                
                                    {{EMBEDS}}
                                
                                    {{COMPONENTS}}
                                
                                    {{EMOJI}}
                                </div>
                            </div>
                        </div>
                    </div>
                """;

        public static final String THREAD = """
                <div class="chatlog__message-group">
                    <div id="chatlog__message-container-{{MESSAGE_ID}}" class="chatlog__message-container" data-message-id="{{MESSAGE_ID}}">
                        <div class="chatlog__message">
                            <div class="chatlog__message-aside">
                               <img class="chatlog__pin-avatar" src="{{THREAD_URL}}" />
                            </div>
                            <div class="chatlog__message-primary">
                                <div class="chatlog__header">
                                </div>
                                
                                <div class="chatlog__content chatlog__markdown" data-message-id="{{MESSAGE_ID}}" id="message-{{MESSAGE_ID}}">
                                    <span class="chatlog__reference-name" title="{{NAME_TAG}}" style="{{USER_COLOUR}}">{{NAME}}</span> started a thread:
                                    <span class="chatlog__thread-name">{{THREAD_NAME}}</span>.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                """;

        public static final String THREAD_ADD = """
                <div class="chatlog__message-group">
                    <div id="chatlog__message-container-{{MESSAGE_ID}}" class="chatlog__message-container" data-message-id="{{MESSAGE_ID}}">
                        <div class="chatlog__message">
                            <div class="chatlog__message-aside">
                               <img class="chatlog__pin-avatar" src="{{THREAD_URL}}" />
                            </div>
                            <div class="chatlog__message-primary">
                                <div class="chatlog__header">
                                </div>
                                
                                <div class="chatlog__content chatlog__markdown" data-message-id="{{MESSAGE_ID}}" id="message-{{MESSAGE_ID}}">
                                    <span class="chatlog__reference-name" title="{{NAME_TAG}}" style="{{USER_COLOUR}}">{{NAME}}</span> added
                                    <span class="chatlog__reference-name" title="{{RECIPIENT_NAME_TAG}}" style="{{RECIPIENT_USER_COLOUR}}">{{RECIPIENT_NAME}}</span> to the thread.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                """;

        public static final String THREAD_REMOVE = """
                <div class="chatlog__message-group">
                    <div id="chatlog__message-container-{{MESSAGE_ID}}" class="chatlog__message-container" data-message-id="{{MESSAGE_ID}}">
                        <div class="chatlog__message">
                            <div class="chatlog__message-aside">
                               <img class="chatlog__pin-avatar" src="{{THREAD_URL}}" />
                            </div>
                            <div class="chatlog__message-primary">
                                <div class="chatlog__header">
                                </div>
                                
                                <div class="chatlog__content chatlog__markdown" data-message-id="{{MESSAGE_ID}}" id="message-{{MESSAGE_ID}}">
                                    <span class="chatlog__reference-name" title="{{NAME_TAG}}" style="{{USER_COLOUR}}">{{NAME}}</span> removed
                                    <span class="chatlog__reference-name" title="{{RECIPIENT_NAME_TAG}}" style="{{RECIPIENT_USER_COLOUR}}">{{RECIPIENT_NAME}}</span> from the thread.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                """;
    }

    public static final class Embed {

        private Embed() {}

        public static final String AUTHOR = """
                <div class="chatlog__embed-author">
                                    <span class="chatlog__embed-author-name">{{AUTHOR}}</span>
                </div>
                """;

        public static final String AUTHOR_ICON = """
                <div class="chatlog__embed-author">
                                    <img class="chatlog__embed-author-icon" src="{{AUTHOR_ICON}}" alt="Author Icon">
                                    <span class="chatlog__embed-author-name">{{AUTHOR}}</span>
                </div>
                """;

        public static final String BODY = """
                <div class=chatlog__embed>
                    <div class=chatlog__embed-color-pill style=background-color:rgba({{EMBED_R}},{{EMBED_G}},{{EMBED_B}},1)></div>
                    <div class=chatlog__embed-content-container>
                        <div class=chatlog__embed-content>
                            <div class=chatlog__embed-text>
                                {{EMBED_AUTHOR}}
                                {{EMBED_TITLE}}
                                
                                {{EMBED_DESC}}
                                <div class="chatlog__embed-fields">
                                    {{EMBED_FIELDS}}
                                </div>
                                
                                
                                {{EMBED_IMAGE}}
                                
                            </div>
                                
                                
                                
                            {{EMBED_THUMBNAIL}}
                                
                        </div>
                                
                        {{EMBED_FOOTER}}
                                
                    </div>
                </div>
                                
                """;

        public static final String DESCRIPTION = """
                <div class=chatlog__embed-description>
                    <span class="markdown preserve-whitespace">{{EMBED_DESC}}</span>
                </div>
                """;

        public static final String FIELD_INLINE = """
                <div class="chatlog__embed-field  chatlog__embed-field--inline ">
                        <div class="chatlog__embed-field-name"><span class="markdown preserve-whitespace">{{FIELD_NAME}}</span></div>
                        <div class="chatlog__embed-field-value"><span class="markdown preserve-whitespace">{{FIELD_VALUE}}</span></div>
                </div>
                                
                """;

        public static final String FIELD = """
                <div class="chatlog__embed-field  chatlog__embed-field">
                        <div class="chatlog__embed-field-name"><span class="markdown preserve-whitespace">{{FIELD_NAME}}</span></div>
                        <div class="chatlog__embed-field-value"><span class="markdown preserve-whitespace">{{FIELD_VALUE}}</span></div>
                </div>
                """;

        public static final String FOOTER = """
                <div class="chatlog__embed-footer">
                    <span class="chatlog__embed-footer-text">{{EMBED_FOOTER}}</span>
                </div>
                """;

        public static final String FOOTER_IMAGE = """
                <div class="chatlog__embed-footer">
                    <img class="chatlog__embed-footer-icon" src="{{EMBED_FOOTER_ICON}}"><span class="chatlog__embed-footer-text">{{EMBED_FOOTER}}</span>
                </div>
                """;

        public static final String IMAGE = """
                <div class="chatlog__embed-image-container">
                    <a class="chatlog__embed-image-link" href="{{EMBED_IMAGE}}">
                    <img class="chatlog__embed-image" src="{{EMBED_IMAGE}}">
                </a>
                </div>
                """;

        public static final String THUMBNAIL = """
                <div class="chatlog__embed-thumbnail-container">
                    <a class="chatlog__embed-thumbnail-link" href="{{EMBED_THUMBNAIL}}">
                    <img class="chatlog__embed-thumbnail" src="{{EMBED_THUMBNAIL}}">
                </a>
                </div>
                """;

        public static final String TITLE = """
                <div class="chatlog__embed-title">
                        <span class="markdown">{{EMBED_TITLE}}</span>
                </div>
                """;
    }

    public static final class Component {

        private Component() {}

        public static final String BUTTON = """
                <div class="chatlog__component-button {{DISABLED}}" style=background-color:{{STYLE}}>
                    <a href="{{URL}}" style=text-decoration:none>
                    <span class="chatlog__button-label">{{EMOJI}}{{LABEL}}{{ICON}}</span>
                    </a>
                </div>
                """;

        public static final String MENU = """
                <div class="chatlog__component-dropdown {{DISABLED}}">
                  <button onclick="showDropdown({{ID}})" class="dropdownButton" id="dropdownButton{{ID}}">{{PLACEHOLDER}} {{ICON}}</button>
                  <div id="dropdownMenuContent{{ID}}">
                    {{CONTENT}}
                  </div>
                </div>
                """;

        public static final String MENU_OPTIONS = """
                <a href="#">
                        <span class="dropdownContentTitle">{{TITLE}}</span>
                        <br>
                        <span class="dropdownContentDesc">{{DESCRIPTION}}</span>
                </a>
                """;

        public static final String MENU_OPTIONS_EMOJI = """
                <div class="chatlog__dropdown-content">
                        <a href="#">
                                <div class="chatlog__dropdown-emoji">
                                        <span class="dropdownContentEmoji">{{EMOJI}}</span>
                                </div>
                                <div class="chatlog__dropdown-text">
                                        <span class="dropdownContentTitle">{{TITLE}}</span>
                                        <br>
                                        <span class="dropdownContentDesc">{{DESCRIPTION}}</span>
                                </div>
                        </a>
                </div>
                """;
    }

    public static final class Attachment {

        private Attachment() {}

        public static final String AUDIO = """
                <div class=chatlog__attachment>
                    <div class="" onclick="">
                        <div class="">
                            <div class="chatlog__attachment-audio-container">
                                <img src="{{ATTACH_ICON}}" class="chatlog__attachment-icon">
                                <div class="chatlog__attachment-filename">
                                    <a href={{ATTACH_URL}}>{{ATTACH_FILE}}</a>
                                </div>
                                <div class="chatlog__attachment-filesize">
                                    {{ATTACH_BYTES}}
                                </div>
                                <audio class="chatlog__attachment-thumbnail" style="padding-top: 5px" controls>
                                    <source src="{{ATTACH_URL}}" alt="Audio Attachment" title="{{ATTACH_FILE}}">
                                </audio>
                            </div>
                        </div>
                    </div>
                </div>
                """;

        public static final String IMAGE = """
                <div class=chatlog__attachment>
                    <a href={{ATTACH_URL}}><img class=chatlog__attachment-thumbnail src={{ATTACH_URL_THUMB}}></a>
                </div>
                """;


        public static final String MESSAGE = """
                <div class=chatlog__attachment>
                    <div class="" onclick="">
                        <div class="">
                            <div class="chatlog__attachment-container">
                                <img src="{{ATTACH_ICON}}" class="chatlog__attachment-icon">
                                <div class="chatlog__attachment-filename">
                                    <a href={{ATTACH_URL}}>{{ATTACH_FILE}}</a>
                                </div>
                                <div class="chatlog__attachment-filesize">
                                    {{ATTACH_BYTES}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                """;

        public static final String VIDEO = """
                <div class=chatlog__attachment>
                  <video class=chatlog__attachment-thumbnail src={{ATTACH_URL}} controls>Video</video>
                </div>
                """;
    }
}