package pl.wsb.fitnesstracker.user_event.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.event.Event;

@Entity
@Table(name = "user_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserEvent {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status", nullable = false)
    private String status;

    public UserEvent(
            final Event event,
            final User user,
            final String status) {
        this.event = event;
        this.user = user;
        this.status = status;
    }
}
