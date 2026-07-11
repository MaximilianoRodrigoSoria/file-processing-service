package com.ar.laboratory.fileprocessingservice.example.application.inbound.command;

import com.ar.laboratory.fileprocessingservice.example.domain.model.Example;

/** Puerto de entrada para crear un Example */
public interface CreateExampleCommand {

    Example execute(Example example);
}
