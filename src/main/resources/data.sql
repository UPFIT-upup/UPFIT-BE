INSERT INTO users (register_at,
                   provider,
                   kakao_id,
                   system_role,
                   level,
                   exp,
                   refresh_token)
VALUES ('2025-07-31 21:00:00', -- registerAt (timestamp)
        'KAKAO', -- provider (EnumType.STRING)
        'kakao_12345', -- kakaoId
        'NORMAL_USER', -- systemRole (EnumType.STRING)
        1, -- level   (nullable)
        0, -- exp     (nullable)
        NULL -- refreshToken (optional)
       );
