package org.apache.hadoop.yarn.server.nodemanager.containermanager.windows.runtime;

import com.google.common.annotations.VisibleForTesting;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.server.nodemanager.Context;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.container.Container;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.runtime.ContainerExecutionException;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.runtime.ContainerRuntime;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.runtime.ContainerRuntimeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is a {@link ContainerRuntime} implementation that delegates all
 * operations to a {@link DefaultWindowsContainerRuntime} instance, or a custom instance
 * depending on whether each instance believes the operation to be within its
 * scope.
 *
 * @see WindowsContainerRuntime#isRuntimeRequested
 */
public class DelegatingWindowsContainerRuntime
    implements WindowsContainerRuntime {

  private static final Logger LOG =
      LoggerFactory.getLogger(DelegatingWindowsContainerRuntime.class);

  private DefaultWindowsContainerRuntime defaultWindowsContainerRuntime;
  private Set<String> allowedRuntimes = new HashSet<>();

  private List<WindowsContainerRuntime> pluggableRuntimes = new ArrayList<>();

  @Override
  public void initialize(Configuration conf, Context nmContext)
      throws ContainerExecutionException {
    String[] configuredWindowsRuntimes = conf.getTrimmedStrings(
        YarnConfiguration.WINDOWS_CONTAINER_RUNTIME_ALLOWED_RUNTIMES,
        YarnConfiguration.DEFAULT_WINDOWS_CONTAINER_RUNTIME_ALLOWED_RUNTIMES);

    // List of default Windows Container Runtime.
    for (String configuredRuntime : configuredWindowsRuntimes) {
      String normRuntime = configuredRuntime.toUpperCase();
      allowedRuntimes.add(normRuntime);
      if (isPluggableRuntime(normRuntime)) {
        WindowsContainerRuntime runtime =
            createPluggableRuntime(conf, configuredRuntime);
        runtime.initialize(conf, nmContext);
        pluggableRuntimes.add(runtime);
      }
    }
  }

  private WindowsContainerRuntime createPluggableRuntime(Configuration conf,
      String configuredRuntime) throws ContainerExecutionException {
    String configKey = String
        .format(YarnConfiguration.WINDOWS_CONTAINER_RUNTIME_CLASS_FMT,
            configuredRuntime);
    Class<? extends WindowsContainerRuntime> clazz =
        conf.getClass(configKey, null, WindowsContainerRuntime.class);
    if (clazz == null) {
      throw new ContainerExecutionException("Invalid runtime set in "
          + YarnConfiguration.WINDOWS_CONTAINER_RUNTIME_ALLOWED_RUNTIMES + " : "
          + configuredRuntime + " : Missing configuration " + configKey);
    }
    return ReflectionUtils.newInstance(clazz, conf);
  }

  private boolean isPluggableRuntime(String runtimeType) {
    for (WindowsRuntimeConstants.RuntimeType type : WindowsRuntimeConstants.RuntimeType
        .values()) {
      if (type.name().equalsIgnoreCase(runtimeType)) {
        return false;
      }
    }
    return true;
  }

  private WindowsContainerRuntime pickContainerRuntime(Container container)
      throws ContainerExecutionException {
    return pickContainerRuntime(container.getLaunchContext().getEnvironment());
  }

  @VisibleForTesting
  WindowsContainerRuntime pickContainerRuntime(Map<String, String> environment)
      throws ContainerExecutionException {
    WindowsContainerRuntime runtime;

    WindowsContainerRuntime pluggableRuntime =
        pickContainerRuntime(environment);
    if (pluggableRuntime != null) {
      runtime = pluggableRuntime;
    } else if (defaultWindowsContainerRuntime != null
        && defaultWindowsContainerRuntime.isRuntimeRequested(environment)) {
      runtime = defaultWindowsContainerRuntime;
    } else {
      throw new ContainerExecutionException("Requested runtime not allowed.");
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug(
          "Using container runtime: " + runtime.getClass().getSimpleName());
    }

    return runtime;
  }

  /**
   * Set default as true
   *
   * @param env the environment variable settings for the operation
   * @return
   */
  @Override
  public boolean isRuntimeRequested(Map<String, String> env) {
    return true;
  }

  @Override
  public void prepareContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {
    Container container = ctx.getContainer();
    WindowsContainerRuntime runtime = pickContainerRuntime(container);
    runtime.prepareContainer(ctx);
  }

  @Override
  public void launchContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {
    Container container = ctx.getContainer();
    WindowsContainerRuntime runtime = pickContainerRuntime(container);
    runtime.launchContainer(ctx);
  }

  @Override
  public void relaunchContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {
    Container container = ctx.getContainer();
    WindowsContainerRuntime runtime = pickContainerRuntime(container);
    runtime.relaunchContainer(ctx);
  }

  @Override
  public void signalContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {
    Container container = ctx.getContainer();
    WindowsContainerRuntime runtime = pickContainerRuntime(container);
    runtime.signalContainer(ctx);
  }

  @Override
  public void reapContainer(ContainerRuntimeContext ctx)
      throws ContainerExecutionException {
    Container container = ctx.getContainer();
    WindowsContainerRuntime runtime = pickContainerRuntime(container);
    runtime.reapContainer(ctx);
  }

  @Override
  public String[] getIpAndHost(Container container)
      throws ContainerExecutionException {
    WindowsContainerRuntime runtime = pickContainerRuntime(container);
    return runtime.getIpAndHost(container);
  }
}
