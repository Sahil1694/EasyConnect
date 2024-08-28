package com.Smart_Contact_Manager.Smart_Contact_Manager.helpers;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    @Builder.Default
    private MessageType type = MessageType.blue;
}
