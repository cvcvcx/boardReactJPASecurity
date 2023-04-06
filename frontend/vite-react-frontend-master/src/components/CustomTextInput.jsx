import { TextField } from "@mui/material";
import React from "react";
import { useController } from "react-hook-form";

const CustomTextInput = ({ name, control, ...props }) => {
  const {
    field: { ref, ...inputProps },
    fieldState: { invalid, error },
  } = useController({ name, control });

  return (
    <TextField
      {...inputProps}
      inputRef={ref}
      error={invalid}
      helperText={error?.message}
      {...props}
    />
  );
};

export default CustomTextInput;
