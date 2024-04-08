package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<JpaUser, Long> {
}
