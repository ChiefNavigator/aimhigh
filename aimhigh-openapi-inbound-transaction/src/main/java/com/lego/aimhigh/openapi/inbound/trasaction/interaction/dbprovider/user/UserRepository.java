package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<JpaUser, Long> {
}
