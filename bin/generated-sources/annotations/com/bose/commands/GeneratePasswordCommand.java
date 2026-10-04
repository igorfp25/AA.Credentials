package com.automation.commands;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.toolchain.runtime.BotRuntimeContext;
import java.lang.Boolean;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Double;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class GeneratePasswordCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(GeneratePasswordCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(globalSessionContext, parameters, sessionMap, null, null);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap,
      Map<String, Value> packageSettings, BotRuntimeContext botRuntimeContext) {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    GeneratePassword command = new GeneratePassword();
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("length") && parameters.get("length") != null && parameters.get("length").get() != null) {
      convertedParameters.put("length", parameters.get("length").get());
      if(convertedParameters.get("length") !=null && !(convertedParameters.get("length") instanceof Double)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","length", "Double", parameters.get("length").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("length") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","length"));
    }

    if(parameters.containsKey("useLowercase") && parameters.get("useLowercase") != null && parameters.get("useLowercase").get() != null) {
      convertedParameters.put("useLowercase", parameters.get("useLowercase").get());
      if(convertedParameters.get("useLowercase") !=null && !(convertedParameters.get("useLowercase") instanceof Boolean)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","useLowercase", "Boolean", parameters.get("useLowercase").get().getClass().getSimpleName()));
      }
    }

    if(parameters.containsKey("useUppercase") && parameters.get("useUppercase") != null && parameters.get("useUppercase").get() != null) {
      convertedParameters.put("useUppercase", parameters.get("useUppercase").get());
      if(convertedParameters.get("useUppercase") !=null && !(convertedParameters.get("useUppercase") instanceof Boolean)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","useUppercase", "Boolean", parameters.get("useUppercase").get().getClass().getSimpleName()));
      }
    }

    if(parameters.containsKey("useNumbers") && parameters.get("useNumbers") != null && parameters.get("useNumbers").get() != null) {
      convertedParameters.put("useNumbers", parameters.get("useNumbers").get());
      if(convertedParameters.get("useNumbers") !=null && !(convertedParameters.get("useNumbers") instanceof Boolean)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","useNumbers", "Boolean", parameters.get("useNumbers").get().getClass().getSimpleName()));
      }
    }

    if(parameters.containsKey("useSpecialCharacters") && parameters.get("useSpecialCharacters") != null && parameters.get("useSpecialCharacters").get() != null) {
      convertedParameters.put("useSpecialCharacters", parameters.get("useSpecialCharacters").get());
      if(convertedParameters.get("useSpecialCharacters") !=null && !(convertedParameters.get("useSpecialCharacters") instanceof Boolean)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","useSpecialCharacters", "Boolean", parameters.get("useSpecialCharacters").get().getClass().getSimpleName()));
      }
    }

    if(parameters.containsKey("specialCharacters") && parameters.get("specialCharacters") != null && parameters.get("specialCharacters").get() != null) {
      convertedParameters.put("specialCharacters", parameters.get("specialCharacters").get());
      if(convertedParameters.get("specialCharacters") !=null && !(convertedParameters.get("specialCharacters") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","specialCharacters", "String", parameters.get("specialCharacters").get().getClass().getSimpleName()));
      }
    }

    try {
      Optional<Value> result =  Optional.ofNullable(command.generatePassword((Double)convertedParameters.get("length"),(Boolean)convertedParameters.get("useLowercase"),(Boolean)convertedParameters.get("useUppercase"),(Boolean)convertedParameters.get("useNumbers"),(Boolean)convertedParameters.get("useSpecialCharacters"),(String)convertedParameters.get("specialCharacters")));
      return logger.traceExit(result);
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","generatePassword"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }

  public Map<String, Value> executeAndReturnMany(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return null;
  }
}
